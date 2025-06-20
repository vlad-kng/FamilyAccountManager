package ru.dorin.familyaccountmanager.application.adapter.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.domain.aggregate.Account;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountId;
import ru.dorin.familyaccountmanager.domain.aggregate.TransactionDTO;
import ru.dorin.familyaccountmanager.domain.event.AccountEvent;
import ru.dorin.familyaccountmanager.domain.event.AccountTransactionEvent;
import ru.dorin.familyaccountmanager.application.utils.MessageResolver;
import ru.dorin.familyaccountmanager.domain.port.EventStore;
import ru.dorin.familyaccountmanager.domain.port.query.AccountQueryService;

import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountQueryServiceImpl implements AccountQueryService {
    private static final ResourceBundle messages = ResourceBundle.getBundle("accountMessages");

    private final EventStore<Account, AccountEvent> eventStore;
    private final MessageResolver messageResolver;

    @Override
    public Account getAccount(AccountId accountId) {
        return Account.recreateFromEvents(eventStore.load(accountId));
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
                        messageResolver.getResolvedMessage(messages, event.getDescription(), event.getAggregateId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> getAccounts(List<AccountId> accountIds) {
        return eventStore.loadAggregates(accountIds, Account::recreateFromEvents);
    }
}