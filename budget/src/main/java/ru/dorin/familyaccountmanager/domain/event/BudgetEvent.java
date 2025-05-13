package ru.dorin.familyaccountmanager.domain.event;


import ru.dorin.familyaccountmanager.domain.aggregate.Budget;

public interface BudgetEvent extends DomainEvent<Budget> {
    @Override
    default Class<Budget> getAggregateClass() {
        return Budget.class;
    }
}
