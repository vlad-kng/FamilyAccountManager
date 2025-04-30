package ru.dorin.familyaccountmanager.application.publisher;

import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

import java.util.List;

public interface DomainEventPublisher {
    <E extends AbstractDomainAggregate<E>, T extends DomainEvent<E>> void publish(T event);
    <E extends AbstractDomainAggregate<E>, T extends DomainEvent<E>> void publish(List<T> eventList);
}
