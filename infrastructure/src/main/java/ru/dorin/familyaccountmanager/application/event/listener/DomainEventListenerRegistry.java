package ru.dorin.familyaccountmanager.application.event.listener;

import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.domain.listener.AbstractStoringEventListener;
import ru.dorin.familyaccountmanager.domain.listener.DomainEventListener;
import ru.dorin.familyaccountmanager.domain.listener.ProcessingEventListener;
import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DomainEventListenerRegistry {

    private final Map<Class<? extends DomainEvent<?>>, List<ProcessingEventListener<?, ?>>> processingListeners;
    private final Map<Class<? extends DomainEvent<?>>, AbstractStoringEventListener<?,?>> storingListeners;


    public DomainEventListenerRegistry(List<ProcessingEventListener<?, ?>> processingListeners,List<AbstractStoringEventListener<?,?>> storingListeners) {
        this.processingListeners = processingListeners.stream()
                .collect(Collectors.groupingBy(ProcessingEventListener::eventType));
        this.storingListeners = storingListeners.stream()
                .collect(Collectors.toMap(
                        AbstractStoringEventListener::eventType,
                        Function.identity()
                ));
    }

    public <Aggregate extends AbstractDomainAggregate<Aggregate>, Event extends DomainEvent<Aggregate>, Listener extends DomainEventListener<Aggregate, Event>> List<Listener>
    getProcessingListenersFor(Class<Event> eventClass) {
        return processingListeners.entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(eventClass))
                .flatMap(entry -> entry.getValue().stream())
                .map(listener -> (Listener) listener)
                .toList();
    }

    public <Aggregate extends AbstractDomainAggregate<Aggregate>, Event extends DomainEvent<Aggregate>> DomainEventListener<Aggregate, Event> getStoringListener(Class<Event> eventClass) {
        return  (DomainEventListener<Aggregate, Event>) storingListeners.get(eventClass);
    }
}