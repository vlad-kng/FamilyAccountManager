package ru.dorin.familyaccountmanager.infrastructure.listener.account;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.application.listener.ProcessingEventListener;
import ru.dorin.familyaccountmanager.domain.port.query.AccountQueryService;
import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.event.account.MoneyWithdrawalEvent;
import ru.dorin.familyaccountmanager.application.integration.event.WithdrawBalanceBudgetEvent;

@Component
@RequiredArgsConstructor
public class WithdrawalEventListener implements ProcessingEventListener<Account, MoneyWithdrawalEvent> {

    private final AccountQueryService accountQueryService;
    private final ApplicationEventPublisher publisher;

    @Override
    public Class<MoneyWithdrawalEvent> eventType() {
        return MoneyWithdrawalEvent.class;
    }

    @Override
    public void afterStore(MoneyWithdrawalEvent event) {
        Account account = accountQueryService.getAccount(event.accountId());
        if (account.getFamilyId() != null) {
            //TODO make ALL accounts with familyId
            var appEvent = new WithdrawBalanceBudgetEvent(account.getFamilyId(), event.category(), event.money(), event.occurredAt());
            publisher.publishEvent(appEvent);
        }
    }
}
