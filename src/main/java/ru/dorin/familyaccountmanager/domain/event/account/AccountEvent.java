package ru.dorin.familyaccountmanager.domain.event.account;

import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.account.TransactionType;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

import java.math.BigDecimal;

public interface AccountEvent extends DomainEvent<Account> {
    BigDecimal getAmount();
    TransactionType getTransactionType();
}
