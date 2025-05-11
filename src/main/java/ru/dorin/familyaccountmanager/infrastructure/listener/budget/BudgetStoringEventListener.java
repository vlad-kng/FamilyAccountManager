package ru.dorin.familyaccountmanager.infrastructure.listener.budget;

import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.platform.domain.listener.AbstractStoringEventListener;
import ru.dorin.familyaccountmanager.platform.domain.port.EventStore;
import ru.dorin.familyaccountmanager.budget.Budget;
import ru.dorin.familyaccountmanager.event.budget.BudgetEvent;

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
