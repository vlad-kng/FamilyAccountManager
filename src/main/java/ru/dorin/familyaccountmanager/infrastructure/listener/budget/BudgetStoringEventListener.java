package ru.dorin.familyaccountmanager.infrastructure.listener.budget;

import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.application.port.EventStore;
import ru.dorin.familyaccountmanager.domain.budget.Budget;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;
import ru.dorin.familyaccountmanager.domain.event.budget.BudgetCreatedEvent;
import ru.dorin.familyaccountmanager.domain.event.listener.AbstractStoringEventListener;

@Component
public class BudgetStoringEventListener extends AbstractStoringEventListener<Budget> {

    public BudgetStoringEventListener(EventStore<Budget, DomainEvent<Budget>> eventStore) {
        super(eventStore);
    }

    @Override
    public Class<BudgetCreatedEvent> eventType() {
        return BudgetCreatedEvent.class;
    }
}
