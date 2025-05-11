package ru.dorin.familyaccountmanager.platform.domain.publisher;

import ru.dorin.familyaccountmanager.platform.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.platform.domain.event.DomainEvent;

import java.util.List;

public interface DomainEventPublisher {
    <E extends AbstractDomainAggregate<E>, T extends DomainEvent<E>> void publish(T event);
    <E extends AbstractDomainAggregate<E>, T extends DomainEvent<E>> void publish(List<T> eventList);
}
