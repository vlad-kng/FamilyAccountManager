package ru.dorin.familyaccountmanager.infrastructure.listener.account;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.application.integration.event.LinkAccountToFamilyIntegrationEvent;
import ru.dorin.familyaccountmanager.application.listener.ProcessingEventListener;
import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.event.account.AccountLinkedEvent;

@Component
@RequiredArgsConstructor
public class AccountLinkedEventListener implements ProcessingEventListener<Account, AccountLinkedEvent> {

    private final ApplicationEventPublisher publisher;

    @Override
    public Class<AccountLinkedEvent> eventType() {
        return AccountLinkedEvent.class;
    }

    @Override
    public void afterStore(AccountLinkedEvent event) {
        var integrationEvent = new LinkAccountToFamilyIntegrationEvent(event.accountId(), event.familyId(), event.occurredAt());
        publisher.publishEvent(integrationEvent);
    }
}
