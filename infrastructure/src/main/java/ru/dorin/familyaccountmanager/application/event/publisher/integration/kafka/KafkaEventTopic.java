package ru.dorin.familyaccountmanager.application.event.publisher.integration.kafka;

import ru.dorin.familyaccountmanager.integration.event.IntegrationEvent;

public abstract class KafkaEventTopic<E extends IntegrationEvent>{
    public abstract String getTopic();
    public abstract Class<E> getEventType();
}
