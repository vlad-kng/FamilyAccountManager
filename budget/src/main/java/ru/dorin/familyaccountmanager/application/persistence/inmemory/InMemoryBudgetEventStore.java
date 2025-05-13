package ru.dorin.familyaccountmanager.application.persistence.inmemory;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.dorin.familyaccountmanager.domain.aggregate.Budget;
import ru.dorin.familyaccountmanager.domain.event.BudgetEvent;
import ru.dorin.familyaccountmanager.domain.port.AbstractInMemoryEventStore;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.eventStore.type", havingValue = "inMemory")
public class InMemoryBudgetEventStore extends AbstractInMemoryEventStore<Budget, BudgetEvent> {
}
