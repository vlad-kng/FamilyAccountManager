package ru.dorin.familyaccountmanager.platform.domain.listener;

import ru.dorin.familyaccountmanager.platform.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.platform.domain.event.DomainEvent;

public interface DomainEventListener <Aggregate extends AbstractDomainAggregate<Aggregate>,E extends DomainEvent<Aggregate>> {
    Class<? extends E> eventType();
}
