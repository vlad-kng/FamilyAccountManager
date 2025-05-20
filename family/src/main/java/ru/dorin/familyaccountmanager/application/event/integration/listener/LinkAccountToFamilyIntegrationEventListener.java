package ru.dorin.familyaccountmanager.application.event.integration.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.domain.aggregate.FamilyId;
import ru.dorin.familyaccountmanager.domain.port.FamilyUseCaseService;
import ru.dorin.familyaccountmanager.integration.event.LinkAccountToFamilyIntegrationEvent;
import ru.dorin.familyaccountmanager.integration.event.listener.IntegrationEventListener;

@Component
@RequiredArgsConstructor
public class LinkAccountToFamilyIntegrationEventListener implements IntegrationEventListener<LinkAccountToFamilyIntegrationEvent> {

    private final FamilyUseCaseService familyUseCaseService;

    @Override
    @EventListener
    public void handle(LinkAccountToFamilyIntegrationEvent event) {
        familyUseCaseService.linkAccountToFamily(new FamilyId(event.familyId()), event.accountId());
    }

    @Override
    public Class<LinkAccountToFamilyIntegrationEvent> getEventType() {
        return LinkAccountToFamilyIntegrationEvent.class;
    }
}
