package ru.dorin.familyaccountmanager.domain.listener;

import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

public interface DomainEventListener <Aggregate extends AbstractDomainAggregate<Aggregate>,E extends DomainEvent<Aggregate>> {
    Class<? extends E> eventType();
}
