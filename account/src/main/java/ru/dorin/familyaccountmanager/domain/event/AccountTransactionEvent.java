package ru.dorin.familyaccountmanager.domain.event;



import ru.dorin.familyaccountmanager.domain.aggregate.TransactionType;

import java.math.BigDecimal;

public interface AccountTransactionEvent extends AccountEvent{
    BigDecimal getAmount();
    TransactionType getTransactionType();
}