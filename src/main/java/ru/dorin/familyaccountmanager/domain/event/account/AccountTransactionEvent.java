package ru.dorin.familyaccountmanager.domain.event.account;

import ru.dorin.familyaccountmanager.domain.account.TransactionType;

import java.math.BigDecimal;

public interface AccountTransactionEvent extends AccountEvent{
    BigDecimal getAmount();
    TransactionType getTransactionType();
}
