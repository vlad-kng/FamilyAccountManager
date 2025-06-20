package ru.dorin.familyaccountmanager.domain.exception;

import ru.dorin.familyaccountmanager.domain.aggregate.AccountId;

import java.math.BigDecimal;

public class InvalidInitialBalanceException extends AccountException {
    private static final String MESSAGE = "account.initial.balance.invalid";
    public InvalidInitialBalanceException(AccountId accountId, BigDecimal amount) {
        super(MESSAGE, accountId.getId(), amount);
    }
}
