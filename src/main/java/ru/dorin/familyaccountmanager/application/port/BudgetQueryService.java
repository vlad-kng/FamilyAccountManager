package ru.dorin.familyaccountmanager.application.port;

import ru.dorin.familyaccountmanager.domain.budget.Budget;
import ru.dorin.familyaccountmanager.domain.budget.BudgetId;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.util.List;

public interface BudgetQueryService {
    Budget getBudget(BudgetId budgetId);
    List<Budget> getBudgets(FamilyId familyId);
    List<Budget> getBudgets(List<BudgetId> budgetIds);
}
