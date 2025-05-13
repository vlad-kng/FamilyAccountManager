package ru.dorin.familyaccountmanager.domain.event;

import ru.dorin.familyaccountmanager.domain.aggregate.Budget;
import ru.dorin.familyaccountmanager.domain.aggregate.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.aggregate.BudgetId;
import ru.dorin.familyaccountmanager.domain.DomainId;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;

import java.time.Instant;

public record BudgetSpentEvent(
        BudgetId budgetId,
        BudgetCategory category,
        Money money,
        Instant occurredAt
) implements BudgetEvent {

    @Override
    public DomainId<Budget> getAggregateId() {
        return budgetId;
    }

    @Override
    public void applyTo(Budget budget) {
        budget.spend(money);
    }

    @Override
    public String getDescription() {
        return "budget.spent";
    }

    @Override
    public Object[] getDescriptionArgs() {
        return new Object[]{budgetId, category.name(), money.amount()};
    }
}