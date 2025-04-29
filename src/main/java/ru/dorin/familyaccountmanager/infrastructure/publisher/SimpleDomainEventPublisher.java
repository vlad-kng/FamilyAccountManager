package ru.dorin.familyaccountmanager.infrastructure.publisher;

import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;
import ru.dorin.familyaccountmanager.domain.event.listener.AbstractStoringEventListener;
import ru.dorin.familyaccountmanager.domain.event.listener.DomainEventListener;
import ru.dorin.familyaccountmanager.domain.event.listener.ProcessingEventListener;
import ru.dorin.familyaccountmanager.domain.event.publisher.DomainEventPublisher;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SimpleDomainEventPublisher implements DomainEventPublisher {

    private final Map<Class<? extends DomainEvent<?>>, List<ProcessingEventListener<?, ?>>> processingListeners;
    private final Map<Class<? extends DomainEvent<?>>, AbstractStoringEventListener<?>> storingListeners;

    public SimpleDomainEventPublisher(List<ProcessingEventListener<?, ?>> processingListeners,List<AbstractStoringEventListener<?>> storingListeners) {
        this.processingListeners = processingListeners.stream()
                .collect(Collectors.groupingBy(ProcessingEventListener::eventType));
        this.storingListeners = storingListeners.stream()
                .collect(Collectors.toMap(
                        AbstractStoringEventListener::eventType,
                        Function.identity()
                ));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E extends AbstractDomainAggregate<E>, T extends DomainEvent<E>> void publish(T event) {
        Class<T> eventType = (Class<T>) event.getClass();

        List<ProcessingEventListener<E, T>> listeners =
                findListenersFor(eventType, processingListeners);

        for (ProcessingEventListener<E, T> listener : listeners) {
            listener.beforeStore(event);
        }

        AbstractStoringEventListener<E> storingListener =
                (AbstractStoringEventListener<E>) storingListeners.get(eventType);
        if (storingListener != null) {
            storingListener.store(event);
        }

        for (ProcessingEventListener<E, T> listener : listeners) {
            listener.afterStore(event);
        }
    }

    private <E extends AbstractDomainAggregate<E>, T extends DomainEvent<E>, L extends DomainEventListener<E, T>> List<L>
    findListenersFor(Class<T> eventClass, Map<Class<? extends DomainEvent<?>>, List<ProcessingEventListener<?, ?>>> listenerMap) {
        return listenerMap.entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(eventClass))
                .flatMap(entry -> entry.getValue().stream())
                .map(listener -> (L) listener)
                .toList();
    }
}