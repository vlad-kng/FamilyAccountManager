package ru.dorin.familyaccountmanager.infrastructure.listener.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.integration.event.LinkAccountToFamilyIntegrationEvent;
import ru.dorin.familyaccountmanager.application.integration.publisher.IntegrationEventPublisher;
import ru.dorin.familyaccountmanager.platform.domain.listener.ProcessingEventListener;
import ru.dorin.familyaccountmanager.account.Account;
import ru.dorin.familyaccountmanager.event.account.AccountLinkedEvent;

@Component
@RequiredArgsConstructor
public class AccountLinkedEventListener implements ProcessingEventListener<Account, AccountLinkedEvent> {

    private final IntegrationEventPublisher publisher;

    @Override
    public Class<AccountLinkedEvent> eventType() {
        return AccountLinkedEvent.class;
    }

    @Override
    public void afterStore(AccountLinkedEvent event) {
        var integrationEvent = new LinkAccountToFamilyIntegrationEvent(event.accountId().getId(), event.familyId(), event.occurredAt());
        publisher.publish(integrationEvent);
    }
}
