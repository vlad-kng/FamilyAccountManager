package ru.dorin.familyaccountmanager.event.account;

import ru.dorin.familyaccountmanager.account.TransactionType;

import java.math.BigDecimal;

public interface AccountTransactionEvent extends AccountEvent{
    BigDecimal getAmount();
    TransactionType getTransactionType();
}