package ru.dorin.familyaccountmanager.event.budget;

import ru.dorin.familyaccountmanager.platform.domain.DomainId;
import ru.dorin.familyaccountmanager.platform.domain.valueobject.Money;
import ru.dorin.familyaccountmanager.budget.Budget;
import ru.dorin.familyaccountmanager.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.budget.BudgetId;

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