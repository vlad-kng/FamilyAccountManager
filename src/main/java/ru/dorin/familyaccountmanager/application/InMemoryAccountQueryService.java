package ru.dorin.familyaccountmanager.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.application.port.AccountQueryService;
import ru.dorin.familyaccountmanager.application.port.EventStore;
import ru.dorin.familyaccountmanager.application.utils.MessageResolver;
import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.account.TransactionDTO;
import ru.dorin.familyaccountmanager.domain.event.account.AccountEvent;
import ru.dorin.familyaccountmanager.domain.event.account.AccountTransactionEvent;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InMemoryAccountQueryService implements AccountQueryService {

    private final EventStore<Account, AccountEvent> eventStore;
    private final MessageResolver messageResolver;

    @Override
    public Account getAccount(AccountId accountId) {
        List<AccountEvent> events = eventStore.load(accountId);
        Account account = new Account(accountId);
        account.recreateFrom(events);
        return account;
    }

    @Override
    public List<TransactionDTO> getTransactions(AccountId accountId) {
        List<AccountEvent> events = eventStore.load(accountId);
        return events.stream()
                .filter(AccountTransactionEvent.class::isInstance)
                .map(AccountTransactionEvent.class::cast)
                .map(event -> new TransactionDTO(
                        event.getTransactionType(),
                        event.getAmount(),
                        event.occurredAt(),
                        messageResolver.getResolvedMessage(event.getDescription(), event.getAggregateId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> getAccounts(List<AccountId> accountIds) {
        return eventStore.loadAggregates(accountIds, Account::new);
    }
}
