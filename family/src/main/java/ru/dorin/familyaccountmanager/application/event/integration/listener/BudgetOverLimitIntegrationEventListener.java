package ru.dorin.familyaccountmanager.application.event.integration.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.integration.event.BudgetOverLimitEvent;
import ru.dorin.familyaccountmanager.integration.event.listener.IntegrationEventListener;

@Component
@Slf4j
public class BudgetOverLimitIntegrationEventListener implements IntegrationEventListener<BudgetOverLimitEvent> {

    @Override
    @EventListener
    public void handle(BudgetOverLimitEvent event) {
        //TODO notify family parents
        log.info("Family budget for category: {} exceeded. limit: {}, spent: {}", event.budgetCategory(), event.limit(), event.spent());
    }

    @Override
    public Class<BudgetOverLimitEvent> getEventType() {
        return BudgetOverLimitEvent.class;
    }
}
