package ru.dorin.familyaccountmanager.application.event.publisher.integration.kafka;

import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.integration.event.IntegrationEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KafkaTopicRegistry {
    private  Map<Class<? extends IntegrationEvent>, KafkaEventTopic<? extends IntegrationEvent>> topicMap;

    public KafkaTopicRegistry(List<KafkaEventTopic<? extends IntegrationEvent>> topics) {
        topicMap = new HashMap<>();
        topics.forEach(topic -> topicMap.put(topic.getEventType(), topic));
    }

    public <E extends IntegrationEvent> KafkaEventTopic<E> getTopic(Class<E> eventType) {
        KafkaEventTopic<E> kafkaEventTopic = (KafkaEventTopic<E>) topicMap.get(eventType);

        return kafkaEventTopic != null ? kafkaEventTopic : getDefaultTopic();
    }

    private <E extends IntegrationEvent> KafkaEventTopic<E> getDefaultTopic() {
        return (KafkaEventTopic<E>) new DefaultIntegrationEventTopic();
    }
}
