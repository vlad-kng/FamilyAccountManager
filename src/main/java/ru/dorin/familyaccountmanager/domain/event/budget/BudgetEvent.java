package ru.dorin.familyaccountmanager.domain.event.budget;

import ru.dorin.familyaccountmanager.domain.budget.Budget;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

public interface BudgetEvent extends DomainEvent<Budget> {
    @Override
    default Class<Budget> getAggregateClass() {
        return Budget.class;
    }
}
