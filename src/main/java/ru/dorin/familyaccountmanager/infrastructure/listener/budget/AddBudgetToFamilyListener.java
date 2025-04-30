package ru.dorin.familyaccountmanager.infrastructure.listener.budget;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.application.port.FamilyService;
import ru.dorin.familyaccountmanager.domain.budget.Budget;
import ru.dorin.familyaccountmanager.domain.event.budget.BudgetCreatedEvent;
import ru.dorin.familyaccountmanager.application.listener.ProcessingEventListener;

@Component
@RequiredArgsConstructor
public class AddBudgetToFamilyListener implements ProcessingEventListener<Budget, BudgetCreatedEvent> {
    private final FamilyService familyService;

    @Override
    public Class<? extends BudgetCreatedEvent> eventType() {
        return BudgetCreatedEvent.class;
    }

    @Override
    public void afterStore(BudgetCreatedEvent event) {
       familyService.addBudget(event.familyId(), event.id());
    }
}
