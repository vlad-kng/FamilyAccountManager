package ru.dorin.familyaccountmanager.exception;

import ru.dorin.familyaccountmanager.platform.domain.exception.LocalizedException;

public class InsufficientFundsException extends LocalizedException {
    //TODO добавить поддержку передачи параметра в сообщение
    public static final String MESSAGE = "account.insufficient.funds";

    public InsufficientFundsException() {
        super(MESSAGE);
    }
}
