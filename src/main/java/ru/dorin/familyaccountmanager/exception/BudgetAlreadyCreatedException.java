package ru.dorin.familyaccountmanager.exception;

import ru.dorin.familyaccountmanager.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.platform.domain.exception.LocalizedException;

import java.util.UUID;

public class BudgetAlreadyCreatedException extends LocalizedException {
    public static final String MESSAGE = "budget.already.created";

    public BudgetAlreadyCreatedException(UUID familyId, BudgetCategory category) {
        super(MESSAGE, familyId, category);
    }
}
