package ru.dorin.familyaccountmanager.application.exception;

import ru.dorin.familyaccountmanager.domain.exception.AccountException;

public class AccountNotFoundException extends AccountException {
    private final static String MESSAGE = "account.not.found";
    public AccountNotFoundException() {
        super(MESSAGE);
    }
}
