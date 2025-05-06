package ru.dorin.familyaccountmanager.domain.event.account;

import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

public interface AccountEvent extends DomainEvent<Account> {
    @Override
    default Class<Account> getAggregateClass() {
        return Account.class;
    }
}
