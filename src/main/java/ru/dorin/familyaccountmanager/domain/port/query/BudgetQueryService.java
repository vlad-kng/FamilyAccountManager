package ru.dorin.familyaccountmanager.domain.port.query;

import ru.dorin.familyaccountmanager.domain.budget.Budget;
import ru.dorin.familyaccountmanager.domain.budget.BudgetId;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.util.List;

public interface BudgetQueryService {
    Budget getBudget(BudgetId budgetId);
    List<Budget> getBudgets(FamilyId familyId);
    List<Budget> getBudgets(List<BudgetId> budgetIds);
}
