package ru.dorin.familyaccountmanager.domain.port;

import ru.dorin.familyaccountmanager.domain.aggregate.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.aggregate.BudgetId;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;

import java.time.Instant;
import java.time.YearMonth;
import java.util.UUID;

public interface BudgetUseCaseService {
    BudgetId createBudget(UUID familyId, BudgetCategory category, YearMonth period, Money limits);
    void spend(UUID familyId, BudgetCategory category, Money money, Instant occurredAt);
}
