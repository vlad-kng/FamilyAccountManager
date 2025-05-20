package ru.dorin.familyaccountmanager.application.event.integration.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.domain.aggregate.FamilyId;
import ru.dorin.familyaccountmanager.domain.port.FamilyUseCaseService;
import ru.dorin.familyaccountmanager.integration.event.AddBudgetIntegrationEvent;
import ru.dorin.familyaccountmanager.integration.event.listener.IntegrationEventListener;

@Component
@RequiredArgsConstructor
public class AddBudgetIntegrationEventListener implements IntegrationEventListener<AddBudgetIntegrationEvent> {

    private final FamilyUseCaseService familyUseCaseService;

    @Override
    @EventListener
    public void handle(AddBudgetIntegrationEvent event) {
        familyUseCaseService.addBudget(new FamilyId(event.familyId()), event.budgetId());
    }

    @Override
    public Class<AddBudgetIntegrationEvent> getEventType() {
        return AddBudgetIntegrationEvent.class;
    }
}
