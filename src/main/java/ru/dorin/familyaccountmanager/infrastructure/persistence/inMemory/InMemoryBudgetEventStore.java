package ru.dorin.familyaccountmanager.infrastructure.persistence.inMemory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.dorin.familyaccountmanager.budget.Budget;
import ru.dorin.familyaccountmanager.event.budget.BudgetEvent;
import ru.dorin.familyaccountmanager.platform.domain.port.AbstractInMemoryEventStore;

@Repository
@RequiredArgsConstructor
public class InMemoryBudgetEventStore extends AbstractInMemoryEventStore<Budget, BudgetEvent> {
}
