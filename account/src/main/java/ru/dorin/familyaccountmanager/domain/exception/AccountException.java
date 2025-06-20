package ru.dorin.familyaccountmanager.domain.exception;

import java.util.ResourceBundle;

public class AccountException extends LocalizedException {
    private static final ResourceBundle messages = ResourceBundle.getBundle("accountExceptions");

    public AccountException(String messageKey, Object... args) {
        super(messageKey, messages, args);
    }
}
