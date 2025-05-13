package ru.dorin.familyaccountmanager.domain.exception;

import java.math.BigDecimal;

public class InvalidMoneyValueException extends LocalizedException {
    public static final String MESSAGE = "money.value.invalid";
    public InvalidMoneyValueException() {
        super(MESSAGE);
    }

    public InvalidMoneyValueException(BigDecimal value) {
        super(MESSAGE, value);
    }
}
