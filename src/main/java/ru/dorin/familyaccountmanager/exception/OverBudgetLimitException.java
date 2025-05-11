package ru.dorin.familyaccountmanager.exception;

import ru.dorin.familyaccountmanager.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.budget.BudgetId;
import ru.dorin.familyaccountmanager.platform.domain.exception.LocalizedException;

public class OverBudgetLimitException extends LocalizedException {
    public static final String MESSAGE = "budget.over.limit";
    public OverBudgetLimitException() {
        super(MESSAGE);
    }

    public OverBudgetLimitException(BudgetId budgetId, BudgetCategory category) {
        super(MESSAGE, budgetId, category);
    }
}
