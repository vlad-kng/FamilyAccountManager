package ru.dorin.familyaccountmanager.application.event.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.domain.aggregate.Account;
import ru.dorin.familyaccountmanager.domain.event.AccountCreatedEvent;
import ru.dorin.familyaccountmanager.domain.listener.ProcessingEventListener;
import ru.dorin.familyaccountmanager.integration.event.publisher.IntegrationEventPublisher;
import ru.dorin.familyaccountmanager.integration.event.LinkAccountToFamilyIntegrationEvent;

@Component
@RequiredArgsConstructor
public class AccountCreatedEventListener implements ProcessingEventListener<Account, AccountCreatedEvent> {

    private final IntegrationEventPublisher publisher;

    @Override
    public Class<AccountCreatedEvent> eventType() {
        return AccountCreatedEvent.class;
    }

    @Override
    public void afterStore(AccountCreatedEvent event) {
        var integrationEvent = new LinkAccountToFamilyIntegrationEvent(event.accountId().getId(), event.familyId(), event.occurredAt());
        publisher.publish(integrationEvent);
    }
}
