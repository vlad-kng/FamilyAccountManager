package ru.dorin.familyaccountmanager.application.event.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.application.event.listener.DomainEventListenerRegistry;
import ru.dorin.familyaccountmanager.domain.aggregate.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;
import ru.dorin.familyaccountmanager.domain.listener.AbstractStoringEventListener;
import ru.dorin.familyaccountmanager.domain.listener.ProcessingEventListener;
import ru.dorin.familyaccountmanager.domain.publisher.DomainEventPublisher;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SimpleDomainEventPublisher implements DomainEventPublisher {

    private final DomainEventListenerRegistry registry;

    @SuppressWarnings("unchecked")
    @Override
    public <Aggregate extends AbstractDomainAggregate<Aggregate>, Event extends DomainEvent<Aggregate>> void publish(Event event) {
        Class<Event> eventType = (Class<Event>) event.getClass();

        List<ProcessingEventListener<Aggregate, Event>> listeners =
                registry.getProcessingListenersFor(eventType);

        for (ProcessingEventListener<Aggregate, Event> listener : listeners) {
            listener.beforeStore(event);
        }

        AbstractStoringEventListener<Aggregate, Event> storingListener =
                (AbstractStoringEventListener<Aggregate, Event>) registry.getStoringListener(getEventInterface(eventType));
        if (storingListener != null) {
            storingListener.store(event);
        }

        for (ProcessingEventListener<Aggregate, Event> listener : listeners) {
            listener.afterStore(event);
        }
    }

    private <Aggregate extends AbstractDomainAggregate<Aggregate>, Event extends DomainEvent<Aggregate>> Class<Event> getEventInterface(Class<Event> eventClass) {
        return (Class<Event>) Arrays.stream(eventClass.getInterfaces()).filter(DomainEvent.class::isAssignableFrom).findFirst().orElse(eventClass);
    }

    @Override
    public <E extends AbstractDomainAggregate<E>, T extends DomainEvent<E>> void publish(List<T> eventList) {
        eventList.forEach(this::publish);
    }
}