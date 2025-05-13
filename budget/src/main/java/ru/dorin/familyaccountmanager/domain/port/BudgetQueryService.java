package ru.dorin.familyaccountmanager.domain.port;


import ru.dorin.familyaccountmanager.domain.aggregate.Budget;
import ru.dorin.familyaccountmanager.domain.aggregate.BudgetId;

import java.util.List;
import java.util.UUID;

public interface BudgetQueryService {
    Budget getBudget(BudgetId budgetId);
    List<Budget> getBudgets(UUID familyId);
    List<Budget> getBudgets(List<BudgetId> budgetIds);
}
