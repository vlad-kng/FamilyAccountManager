package ru.dorin.familyaccountmanager.application.event.publisher.integration.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.integration.event.IntegrationEvent;
import ru.dorin.familyaccountmanager.integration.event.publisher.IntegrationEventPublisher;

import java.util.List;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.integration.publisher", havingValue = "kafka")
public class KafkaIntegrationEventPublisher implements IntegrationEventPublisher {

    private final KafkaIntegrationEventSender kafkaSender;

    @Override
    public <E extends IntegrationEvent> void publish(E event) {
        kafkaSender.send(event);
    }

    @Override
    public <E extends IntegrationEvent> void publish(List<E> eventList) {
        eventList.forEach(this::publish);
    }
}
