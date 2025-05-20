package ru.dorin.familyaccountmanager.application.event.publisher.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.integration.event.IntegrationEvent;
import ru.dorin.familyaccountmanager.integration.event.publisher.IntegrationEventPublisher;

import java.util.List;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.kafka.enabled", havingValue = "false")
public class ContextIntegrationEventPublisher implements IntegrationEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public <E extends IntegrationEvent> void publish(E event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public <E extends IntegrationEvent> void publish(List<E> eventList) {
        eventList.forEach(applicationEventPublisher::publishEvent);
    }
}
