package ru.dorin.familyaccountmanager.domain.exception;

import ru.dorin.familyaccountmanager.domain.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;
import ru.dorin.familyaccountmanager.infrastructure.exception.LocalizedException;

public class BudgetAlreadyCreatedException extends LocalizedException {
    public static final String MESSAGE = "budget.already.created";

    public BudgetAlreadyCreatedException(FamilyId familyId, BudgetCategory category) {
        super(MESSAGE, familyId, category);
    }
}
