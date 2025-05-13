package ru.dorin.familyaccountmanager.integration.event;

import java.util.List;

public interface IntegrationEventPublisher {
    <E extends IntegrationEvent> void publish(E event);
    <E extends IntegrationEvent> void publish(List<E> eventList);
}
