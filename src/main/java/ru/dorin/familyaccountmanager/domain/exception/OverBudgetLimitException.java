package ru.dorin.familyaccountmanager.domain.exception;

import ru.dorin.familyaccountmanager.domain.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.budget.BudgetId;
import ru.dorin.familyaccountmanager.infrastructure.exception.LocalizedException;

public class OverBudgetLimitException extends LocalizedException {
    public static final String MESSAGE = "budget.over.limit";
    public OverBudgetLimitException() {
        super(MESSAGE);
    }

    public OverBudgetLimitException(BudgetId budgetId, BudgetCategory category) {
        super(MESSAGE, budgetId, category);
    }
}
