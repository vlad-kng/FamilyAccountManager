package ru.dorin.familyaccountmanager.application.event.integration.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.domain.aggregate.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.port.BudgetUseCaseService;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;
import ru.dorin.familyaccountmanager.integration.event.WithdrawBalanceBudgetEvent;
import ru.dorin.familyaccountmanager.integration.event.listener.IntegrationEventListener;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class WithdrawBalanceBudgetIntegrationEventListener implements IntegrationEventListener<WithdrawBalanceBudgetEvent> {

    private final BudgetUseCaseService budgetUseCaseService;

    @Override
    @EventListener
    public void handle(WithdrawBalanceBudgetEvent event) {
        budgetUseCaseService.spend(event.familyId(), BudgetCategory.valueOf(event.category()), new Money(new BigDecimal(event.money())), event.occurredAt());
    }

    @Override
    public Class<WithdrawBalanceBudgetEvent> getEventType() {
        return WithdrawBalanceBudgetEvent.class;
    }
}
