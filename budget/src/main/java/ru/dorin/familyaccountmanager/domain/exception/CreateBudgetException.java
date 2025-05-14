package ru.dorin.familyaccountmanager.domain.exception;

import java.util.UUID;

public class CreateBudgetException extends LocalizedException {
    private static final String MESSAGE = "budget.family.not.found";
    public CreateBudgetException(UUID familyId) {
        super(MESSAGE, familyId);
    }
}
