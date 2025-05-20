package ru.dorin.familyaccountmanager.application.event.domain.listener;

import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.domain.aggregate.Budget;
import ru.dorin.familyaccountmanager.domain.listener.AbstractStoringEventListener;
import ru.dorin.familyaccountmanager.domain.port.EventStore;
import ru.dorin.familyaccountmanager.domain.event.BudgetEvent;

@Component
public class BudgetStoringEventListener extends AbstractStoringEventListener<Budget, BudgetEvent> {

    public BudgetStoringEventListener(EventStore<Budget, BudgetEvent> eventStore) {
        super(eventStore);
    }

    @Override
    public Class<BudgetEvent> eventType() {
        return BudgetEvent.class;
    }
}
