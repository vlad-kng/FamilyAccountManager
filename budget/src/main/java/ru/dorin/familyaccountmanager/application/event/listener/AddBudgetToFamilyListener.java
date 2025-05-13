package ru.dorin.familyaccountmanager.application.event.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.domain.aggregate.Budget;
import ru.dorin.familyaccountmanager.integration.event.AddBudgetIntegrationEvent;
import ru.dorin.familyaccountmanager.integration.event.IntegrationEventPublisher;
import ru.dorin.familyaccountmanager.domain.listener.ProcessingEventListener;
import ru.dorin.familyaccountmanager.domain.event.BudgetCreatedEvent;

@Component
@RequiredArgsConstructor
public class AddBudgetToFamilyListener implements ProcessingEventListener<Budget, BudgetCreatedEvent> {
    private final IntegrationEventPublisher publisher;

    @Override
    public Class<? extends BudgetCreatedEvent> eventType() {
        return BudgetCreatedEvent.class;
    }

    @Override
    public void afterStore(BudgetCreatedEvent event) {
        var integrationEvent = new AddBudgetIntegrationEvent(event.id().getId(), event.familyId());
        publisher.publish(integrationEvent);
    }
}
