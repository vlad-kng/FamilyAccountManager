package ru.dorin.familyaccountmanager.infrastructure.persistence.inMemory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.dorin.familyaccountmanager.domain.budget.Budget;
import ru.dorin.familyaccountmanager.domain.event.budget.BudgetEvent;

@Repository
@RequiredArgsConstructor
public class InMemoryBudgetEventStore extends AbstractInMemoryEventStore<Budget, BudgetEvent> {
}
