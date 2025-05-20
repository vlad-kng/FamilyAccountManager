package ru.dorin.familyaccountmanager.application.event.listener.integration;

import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.integration.event.IntegrationEvent;
import ru.dorin.familyaccountmanager.integration.event.listener.IntegrationEventListener;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class IntegrationEventListenerRegistry {

    private final Map<Class<? extends IntegrationEvent>, IntegrationEventListener<?>> listeners;

    public IntegrationEventListenerRegistry(List<IntegrationEventListener<?>> integrationEventListeners) {
        listeners = integrationEventListeners.stream().collect(Collectors.toMap(
                IntegrationEventListener::getEventType,
                Function.identity()));
    }

    public <Event extends IntegrationEvent> IntegrationEventListener<Event> getListenerFor(Event event) {
        return (IntegrationEventListener<Event>) listeners.get(event.getClass());
    }
}
