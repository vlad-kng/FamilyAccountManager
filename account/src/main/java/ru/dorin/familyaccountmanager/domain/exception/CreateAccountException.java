package ru.dorin.familyaccountmanager.domain.exception;

import java.util.UUID;

public class CreateAccountException extends AccountException {
    private static final String MESSAGE = "account.family.not.found";
    public CreateAccountException(UUID familyId) {
        super(MESSAGE, familyId);
    }
}
