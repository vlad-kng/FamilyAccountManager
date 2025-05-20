package ru.dorin.familyaccountmanager.integration.event.listener;

import ru.dorin.familyaccountmanager.integration.event.IntegrationEvent;

public interface IntegrationEventListener <E extends IntegrationEvent> {
    void handle(E event);
    Class<E> getEventType();
}
