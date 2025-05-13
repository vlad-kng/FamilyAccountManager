package ru.dorin.familyaccountmanager.application.exception;

public class AccountNotFoundException extends RuntimeException {
    private final static String MESSAGE = "account.not.found";
    public AccountNotFoundException() {
        super(MESSAGE);
    }
}
