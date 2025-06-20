package ru.dorin.familyaccountmanager.domain.exception;

import ru.dorin.familyaccountmanager.domain.aggregate.BudgetCategory;

import java.util.UUID;

public class BudgetAlreadyCreatedException extends BudgetException {
    public static final String MESSAGE = "budget.already.created";

    public BudgetAlreadyCreatedException(UUID familyId, BudgetCategory category) {
        super(MESSAGE, familyId, category);
    }
}
