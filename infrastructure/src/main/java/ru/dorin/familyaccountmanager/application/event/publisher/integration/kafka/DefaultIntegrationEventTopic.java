package ru.dorin.familyaccountmanager.application.event.publisher.integration.kafka;

import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.integration.event.IntegrationEvent;

@Component
public class DefaultIntegrationEventTopic extends KafkaEventTopic<IntegrationEvent>{
    @Override
    public String getTopic() {
        return "family-account-manager.integration-event";
    }

    @Override
    public Class<IntegrationEvent> getEventType() {
        return IntegrationEvent.class;
    }
}
