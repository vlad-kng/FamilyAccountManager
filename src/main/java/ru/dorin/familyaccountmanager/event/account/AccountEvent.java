package ru.dorin.familyaccountmanager.event.account;

import ru.dorin.familyaccountmanager.account.Account;
import ru.dorin.familyaccountmanager.platform.domain.event.DomainEvent;

public interface AccountEvent extends DomainEvent<Account> {
    @Override
    default Class<Account> getAggregateClass() {
        return Account.class;
    }
}
