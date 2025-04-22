package ru.dorin.familyaccountmanager.domain.event.budget;

import ru.dorin.familyaccountmanager.domain.DomainId;
import ru.dorin.familyaccountmanager.domain.budget.Budget;
import ru.dorin.familyaccountmanager.domain.budget.BudgetId;
import ru.dorin.familyaccountmanager.domain.budget.BudgetLimits;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.time.Instant;
import java.time.YearMonth;

public record BudgetCreatedEvent(
        BudgetId id,
        FamilyId familyId,
        YearMonth period,
        BudgetLimits limits,
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
        budget.setFamilyId(familyId);
        budget.setPeriod(period);
        budget.setLimits(limits);
    }

    @Override
    public String getDescription() {
        return "budget.created";
    }
}
