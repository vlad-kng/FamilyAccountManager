package ru.dorin.familyaccountmanager.domain.exception;

import ru.dorin.familyaccountmanager.domain.aggregate.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.aggregate.BudgetId;

public class OverBudgetLimitException extends LocalizedException {
    public static final String MESSAGE = "budget.over.limit";
    public OverBudgetLimitException() {
        super(MESSAGE);
    }

    public OverBudgetLimitException(BudgetId budgetId, BudgetCategory category) {
        super(MESSAGE, budgetId, category);
    }
}
