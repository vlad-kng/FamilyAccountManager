package ru.dorin.familyaccountmanager.domain.exception;

import java.math.BigDecimal;

public class NotEnoughMoneyException extends LocalizedException {
    public static final String MESSAGE = "money.not.enough";
    public NotEnoughMoneyException() {
        super(MESSAGE);
    }

    public NotEnoughMoneyException(BigDecimal balance, BigDecimal withdrawal) {
        super(MESSAGE, balance, withdrawal);
    }
}
