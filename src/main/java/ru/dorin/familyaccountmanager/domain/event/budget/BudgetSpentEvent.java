package ru.dorin.familyaccountmanager.domain.event.budget;

import ru.dorin.familyaccountmanager.domain.DomainId;
import ru.dorin.familyaccountmanager.domain.account.Money;
import ru.dorin.familyaccountmanager.domain.budget.Budget;
import ru.dorin.familyaccountmanager.domain.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.budget.BudgetId;

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
        budget.spend(category, money);
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