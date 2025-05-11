package ru.dorin.familyaccountmanager.port.query;

import ru.dorin.familyaccountmanager.budget.Budget;
import ru.dorin.familyaccountmanager.budget.BudgetId;

import java.util.List;
import java.util.UUID;

public interface BudgetQueryService {
    Budget getBudget(BudgetId budgetId);
    List<Budget> getBudgets(UUID familyId);
    List<Budget> getBudgets(List<BudgetId> budgetIds);
}
