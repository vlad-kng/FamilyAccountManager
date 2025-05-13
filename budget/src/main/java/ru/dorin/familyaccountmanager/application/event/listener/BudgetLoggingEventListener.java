package ru.dorin.familyaccountmanager.application.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.domain.aggregate.Budget;
import ru.dorin.familyaccountmanager.domain.event.BudgetEvent;
import ru.dorin.familyaccountmanager.domain.listener.ProcessingEventListener;

@Component
@Slf4j
public class BudgetLoggingEventListener implements ProcessingEventListener<Budget, BudgetEvent> {

    @Override
    public Class<BudgetEvent> eventType() {
        return BudgetEvent.class;
    }

    @Override
    public void beforeStore(BudgetEvent event) {
        log.info("before store budget event: {}", event);
    }

    @Override
    public void afterStore(BudgetEvent event) {
        log.info("after store budget event: {}", event);
    }
}
