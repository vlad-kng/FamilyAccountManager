package ru.dorin.familyaccountmanager.application.port;

import ru.dorin.familyaccountmanager.domain.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.budget.BudgetId;
import ru.dorin.familyaccountmanager.domain.budget.BudgetLimits;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.math.BigDecimal;
import java.time.YearMonth;

public interface BudgetService {
    BudgetId createBudget(FamilyId familyId, YearMonth period, BudgetLimits limits);
    boolean spend(BudgetId budgetId, BudgetCategory budgetCategory, BigDecimal amount);
}
