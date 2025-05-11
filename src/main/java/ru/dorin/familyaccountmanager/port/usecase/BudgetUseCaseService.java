package ru.dorin.familyaccountmanager.port.usecase;

import ru.dorin.familyaccountmanager.platform.domain.valueobject.Money;
import ru.dorin.familyaccountmanager.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.budget.BudgetId;

import java.time.Instant;
import java.time.YearMonth;
import java.util.UUID;

public interface BudgetUseCaseService {
    BudgetId createBudget(UUID familyId, BudgetCategory category, YearMonth period, Money limits);
    void spend(UUID familyId, BudgetCategory category, Money money, Instant occurredAt);
}
