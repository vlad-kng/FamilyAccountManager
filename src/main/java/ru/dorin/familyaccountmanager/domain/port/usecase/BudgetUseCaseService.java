package ru.dorin.familyaccountmanager.domain.port.usecase;

import ru.dorin.familyaccountmanager.domain.account.Money;
import ru.dorin.familyaccountmanager.domain.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.budget.BudgetId;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.time.Instant;
import java.time.YearMonth;

public interface BudgetUseCaseService {
    BudgetId createBudget(FamilyId familyId, BudgetCategory category, YearMonth period, Money limits);
    void spend(FamilyId familyId, BudgetCategory category, Money money, Instant occurredAt);
}
