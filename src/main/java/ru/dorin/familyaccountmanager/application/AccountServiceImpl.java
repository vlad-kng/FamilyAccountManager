package ru.dorin.familyaccountmanager.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.application.port.AccountService;
import ru.dorin.familyaccountmanager.application.port.EventStore;
import ru.dorin.familyaccountmanager.application.utils.MessageResolver;
import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.account.AccountName;
import ru.dorin.familyaccountmanager.domain.account.AccountType;
import ru.dorin.familyaccountmanager.domain.account.Money;
import ru.dorin.familyaccountmanager.domain.account.TransactionDTO;
import ru.dorin.familyaccountmanager.domain.event.account.AccountCreatedEvent;
import ru.dorin.familyaccountmanager.domain.event.account.AccountEvent;
import ru.dorin.familyaccountmanager.domain.event.account.InitialBalanceEvent;
import ru.dorin.familyaccountmanager.domain.event.account.MoneyDepositedEvent;
import ru.dorin.familyaccountmanager.domain.event.account.MoneyTransferReceivedEvent;
import ru.dorin.familyaccountmanager.domain.event.account.MoneyWithdrawalEvent;
import ru.dorin.familyaccountmanager.domain.event.account.TransferMoneyEvent;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final EventStore<Account, AccountEvent> eventStore;
    private final MessageResolver messageResolver;

    @Override
    public AccountId createAccount(String name, AccountType type, String initialBalance) {
        AccountId id = new AccountId();
        AccountName accountName = new AccountName(name);
        BigDecimal balance = new BigDecimal(initialBalance);
        Money money = new Money(balance);
        var createdEvent = new AccountCreatedEvent(id, accountName, type, money, Instant.now());
        var initialBalanceIncreaseEvent = new InitialBalanceEvent(id, money, Instant.now());
        eventStore.append(id, createdEvent);
        eventStore.append(id, initialBalanceIncreaseEvent);
        return id;
    }

    @Override
    public boolean increaseBalance(AccountId accountId, BigDecimal amount) {
        var event = new MoneyDepositedEvent(accountId, Instant.now(), new Money(amount));
        eventStore.append(accountId, event);
        return true;
    }

    @Override
    public boolean withdrawBalance(AccountId accountId, BigDecimal amount) {
        var event = new MoneyWithdrawalEvent(accountId, Instant.now(), new Money(amount));
        eventStore.append(accountId, event);
        return true;
    }

    public Account getAccount(AccountId accountId) {
        List<AccountEvent> events = eventStore.load(accountId);
        Account account = new Account(accountId);
        return account.recreateFrom(events);
    }

    @Override
    public List<TransactionDTO> getTransactions(AccountId accountId) {
        List<AccountEvent> events = eventStore.load(accountId);
        return events.stream()
                .filter(event -> Objects.nonNull(event.getTransactionType()))
                .map(event -> new TransactionDTO(
                        event.getTransactionType(),
                        event.getAmount(),
                        event.occurredAt(),
                        messageResolver.getResolvedMessage(event.getDescription(), event.getAggregateId())))
                .collect(Collectors.toList());
    }


    @Override
    public boolean transferMoney(AccountId from, AccountId to, Money money) {
        var transferEvent = new TransferMoneyEvent(from, to, Instant.now(), money);
        var receiveMoneyEvent = new MoneyTransferReceivedEvent(to, from, Instant.now(), money);
        eventStore.append(from, transferEvent);
        eventStore.append(to, receiveMoneyEvent);
        return true;
    }

    @Override
    public List<Account> getAccounts(List<AccountId> accountIds) {
        return eventStore.loadAggregates(accountIds, Account::new);
    }
}
