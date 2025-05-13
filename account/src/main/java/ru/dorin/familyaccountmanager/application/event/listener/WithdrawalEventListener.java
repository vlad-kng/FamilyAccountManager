package ru.dorin.familyaccountmanager.application.event.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.domain.aggregate.Account;
import ru.dorin.familyaccountmanager.domain.event.MoneyWithdrawalEvent;
import ru.dorin.familyaccountmanager.domain.port.query.AccountQueryService;
import ru.dorin.familyaccountmanager.integration.event.WithdrawBalanceBudgetEvent;
import ru.dorin.familyaccountmanager.integration.event.IntegrationEventPublisher;
import ru.dorin.familyaccountmanager.domain.listener.ProcessingEventListener;

@Component
@RequiredArgsConstructor
public class WithdrawalEventListener implements ProcessingEventListener<Account, MoneyWithdrawalEvent> {

    private final AccountQueryService accountQueryService;
    private final IntegrationEventPublisher publisher;

    @Override
    public Class<MoneyWithdrawalEvent> eventType() {
        return MoneyWithdrawalEvent.class;
    }

    @Override
    public void afterStore(MoneyWithdrawalEvent event) {
        Account account = accountQueryService.getAccount(event.accountId());
        if (account.getFamilyId() != null) {
            //TODO make ALL accounts with familyId
            var appEvent = new WithdrawBalanceBudgetEvent(account.getFamilyId(), event.category(), event.money().amount().toString(), event.occurredAt());
            publisher.publish(appEvent);
        }
    }
}
