package ru.dorin.familyaccountmanager.event.budget;

import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.dorin.familyaccountmanager.budget.Budget;
import ru.dorin.familyaccountmanager.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.budget.BudgetId;
import ru.dorin.familyaccountmanager.platform.domain.DomainId;
import ru.dorin.familyaccountmanager.platform.domain.valueobject.Money;

import java.time.Instant;
import java.time.YearMonth;
import java.util.UUID;

@JsonTypeName("BudgetCreated")
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
