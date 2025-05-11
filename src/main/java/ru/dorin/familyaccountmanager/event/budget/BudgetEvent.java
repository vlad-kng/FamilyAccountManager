package ru.dorin.familyaccountmanager.event.budget;

import ru.dorin.familyaccountmanager.budget.Budget;
import ru.dorin.familyaccountmanager.platform.domain.event.DomainEvent;

public interface BudgetEvent extends DomainEvent<Budget> {
    @Override
    default Class<Budget> getAggregateClass() {
        return Budget.class;
    }
}
