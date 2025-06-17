package ru.dorin.familyaccountmanager.application.event.publisher.integration.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.integration.event.IntegrationEvent;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class KafkaIntegrationEventSender {

    private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;
    private final KafkaTopicRegistry kafkaTopicRegistry;

    public <E extends IntegrationEvent> CompletableFuture<SendResult<String, IntegrationEvent>> send(E event) {
        KafkaEventTopic<E> topic = kafkaTopicRegistry.getTopic((Class<E>) event.getClass());
        if (topic == null) {
            throw new IllegalArgumentException("No topic found for event type: " + event.getClass());
        }
        return kafkaTemplate.send(topic.getTopic(), event.getAggregateId().toString(), event);
    }
}
