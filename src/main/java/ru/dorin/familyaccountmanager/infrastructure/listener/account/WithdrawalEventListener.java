package ru.dorin.familyaccountmanager.infrastructure.listener.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.integration.event.WithdrawBalanceBudgetEvent;
import ru.dorin.familyaccountmanager.application.integration.publisher.IntegrationEventPublisher;
import ru.dorin.familyaccountmanager.platform.domain.listener.ProcessingEventListener;
import ru.dorin.familyaccountmanager.account.Account;
import ru.dorin.familyaccountmanager.event.account.MoneyWithdrawalEvent;
import ru.dorin.familyaccountmanager.port.query.AccountQueryService;

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
            var appEvent = new WithdrawBalanceBudgetEvent(account.getFamilyId(), event.category().name(), event.money().amount().toString(), event.occurredAt());
            publisher.publish(appEvent);
        }
    }
}
