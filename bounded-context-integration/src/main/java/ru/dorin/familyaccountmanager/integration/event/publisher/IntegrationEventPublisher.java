package ru.dorin.familyaccountmanager.integration.event.publisher;

import ru.dorin.familyaccountmanager.integration.event.IntegrationEvent;

import java.util.List;

public interface IntegrationEventPublisher {
    <E extends IntegrationEvent> void publish(E event);
    <E extends IntegrationEvent> void publish(List<E> eventList);
}
