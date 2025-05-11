package ru.dorin.familyaccountmanager.platform.domain.listener;

import ru.dorin.familyaccountmanager.platform.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.platform.domain.event.DomainEvent;

public interface ProcessingEventListener<Aggregate extends AbstractDomainAggregate<Aggregate>, E extends DomainEvent<Aggregate>> extends DomainEventListener <Aggregate, E> {
    default void beforeStore(E event) {}
    default void afterStore(E event) {}
}
