package ru.dorin.familyaccountmanager.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.dorin.familyaccountmanager.domain.budget.Budget;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

@Repository
@RequiredArgsConstructor
public class InMemoryBudgetEventStore extends AbstractInMemoryEventStore<Budget, DomainEvent<Budget>> {
}
