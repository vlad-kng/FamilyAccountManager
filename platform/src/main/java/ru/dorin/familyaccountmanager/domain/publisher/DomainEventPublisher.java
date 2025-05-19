package ru.dorin.familyaccountmanager.domain.publisher;

import ru.dorin.familyaccountmanager.domain.aggregate.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

import java.util.List;

public interface DomainEventPublisher {
    <E extends AbstractDomainAggregate<E>, T extends DomainEvent<E>> void publish(T event);
    <E extends AbstractDomainAggregate<E>, T extends DomainEvent<E>> void publish(List<T> eventList);
}
