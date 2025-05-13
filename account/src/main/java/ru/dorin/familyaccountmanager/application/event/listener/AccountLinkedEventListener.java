package ru.dorin.familyaccountmanager.application.event.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.domain.aggregate.Account;
import ru.dorin.familyaccountmanager.domain.event.AccountLinkedEvent;
import ru.dorin.familyaccountmanager.integration.event.LinkAccountToFamilyIntegrationEvent;
import ru.dorin.familyaccountmanager.integration.event.IntegrationEventPublisher;
import ru.dorin.familyaccountmanager.domain.listener.ProcessingEventListener;


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
