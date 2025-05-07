package ru.dorin.familyaccountmanager.infrastructure.listener.budget;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.application.integration.event.AddBudgetIntegrationEvent;
import ru.dorin.familyaccountmanager.application.integration.publisher.IntegrationEventPublisher;
import ru.dorin.familyaccountmanager.application.listener.ProcessingEventListener;
import ru.dorin.familyaccountmanager.domain.budget.Budget;
import ru.dorin.familyaccountmanager.domain.event.budget.BudgetCreatedEvent;

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
        var integrationEvent = new AddBudgetIntegrationEvent(event.id(), event.familyId());
        publisher.publish(integrationEvent);
    }
}
