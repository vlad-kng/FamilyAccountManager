package ru.dorin.familyaccountmanager.domain.event;


import ru.dorin.familyaccountmanager.domain.aggregate.Account;

public interface AccountEvent extends DomainEvent<Account> {
    @Override
    default Class<Account> getAggregateClass() {
        return Account.class;
    }
}
