package ru.dorin.familyaccountmanager.application.adapter.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.account.Account;
import ru.dorin.familyaccountmanager.account.AccountId;
import ru.dorin.familyaccountmanager.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.event.account.AccountLinkedEvent;
import ru.dorin.familyaccountmanager.event.account.MoneyDepositedEvent;
import ru.dorin.familyaccountmanager.event.account.MoneyTransferReceivedEvent;
import ru.dorin.familyaccountmanager.event.account.MoneyWithdrawalEvent;
import ru.dorin.familyaccountmanager.event.account.TransferMoneyEvent;
import ru.dorin.familyaccountmanager.port.query.AccountQueryService;
import ru.dorin.familyaccountmanager.port.usecase.AccountUseCaseService;
import ru.dorin.familyaccountmanager.platform.domain.exception.NotEnoughMoneyException;
import ru.dorin.familyaccountmanager.platform.domain.publisher.DomainEventPublisher;
import ru.dorin.familyaccountmanager.platform.domain.valueobject.Money;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountUseCaseServiceImpl implements AccountUseCaseService {
    private final AccountQueryService accountQueryService;
    private final DomainEventPublisher publisher;

    @Override
    public boolean increaseBalance(AccountId accountId, BigDecimal amount) {
        var event = new MoneyDepositedEvent(accountId, Instant.now(), new Money(amount));
        publisher.publish(event);
        return true;
    }

    @Override
    public boolean withdrawBalance(AccountId accountId, BigDecimal amount, BudgetCategory category) {
        Money money = new Money(amount);
        Account account = accountQueryService.getAccount(accountId);
        if (!account.canWithdrawal(money)) {
            throw new NotEnoughMoneyException(account.getBalance().amount(), amount);
        }
        Instant occurredAt = Instant.now();
        var event = new MoneyWithdrawalEvent(accountId, category, occurredAt, money);
        publisher.publish(event);
        return true;
    }

    @Override
    public void linkAccountToFamily(UUID accountId, UUID familyId) {
        var event = new AccountLinkedEvent(new AccountId(accountId), familyId, Instant.now());
        publisher.publish(event);
    }

    @Override
    public boolean transferMoney(AccountId from, AccountId to, Money money) {
        var transferEvent = new TransferMoneyEvent(from, to, Instant.now(), money);
        var receiveMoneyEvent = new MoneyTransferReceivedEvent(to, from, Instant.now(), money);
        publisher.publish(List.of(transferEvent, receiveMoneyEvent));
        return true;
    }
}
