package ru.dorin.familyaccountmanager.application.event.publisher.integration.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.integration.event.IntegrationEvent;
import ru.dorin.familyaccountmanager.integration.event.publisher.IntegrationEventPublisher;

import java.util.List;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.kafka.enabled", havingValue = "true")
public class KafkaIntegrationEventPublisher implements IntegrationEventPublisher {

    private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;
    private final KafkaTopicRegistry kafkaTopicRegistry;

    @Override
    public <E extends IntegrationEvent> void publish(E event) {
        KafkaEventTopic<E> topic = kafkaTopicRegistry.getTopic((Class<E>) event.getClass());
        if (topic == null) {
            throw new IllegalArgumentException("No topic found for event type: " + event.getClass());
        }
        kafkaTemplate.send(topic.getTopic(), event.getAggregateId().toString(), event);
    }

    @Override
    public <E extends IntegrationEvent> void publish(List<E> eventList) {
        eventList.forEach(this::publish);
    }
}
