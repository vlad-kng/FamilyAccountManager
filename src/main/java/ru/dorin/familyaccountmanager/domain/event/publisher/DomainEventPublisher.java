package ru.dorin.familyaccountmanager.domain.event.publisher;

import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

public interface DomainEventPublisher {
    <E extends AbstractDomainAggregate<E>, T extends DomainEvent<E>> void publish(T event);
}
