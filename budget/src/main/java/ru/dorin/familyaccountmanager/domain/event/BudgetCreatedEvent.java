package ru.dorin.familyaccountmanager.domain.event;

import ru.dorin.familyaccountmanager.domain.aggregate.Budget;
import ru.dorin.familyaccountmanager.domain.aggregate.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.aggregate.BudgetId;
import ru.dorin.familyaccountmanager.domain.DomainId;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;

import java.time.Instant;
import java.time.YearMonth;
import java.util.UUID;

public record BudgetCreatedEvent(
        BudgetId id,
        UUID familyId,
        BudgetCategory category,
        YearMonth period,
        Money limits,
        Instant occurredAt
) implements BudgetEvent {


    @Override
    public DomainId<Budget> getAggregateId() {
        return id;
    }

    @Override
    public Instant occurredAt() {
        return occurredAt;
    }

    @Override
    public void applyTo(Budget budget) {
        budget.setId(id);
        budget.setFamilyId(familyId);
        budget.setCategory(category);
        budget.setPeriod(period);
        budget.setLimits(limits);
        budget.setSpent(Money.zero());
    }

    @Override
    public String getDescription() {
        return "budget.created";
    }
}
