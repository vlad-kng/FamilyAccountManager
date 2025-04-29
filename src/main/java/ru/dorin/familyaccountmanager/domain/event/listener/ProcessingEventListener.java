package ru.dorin.familyaccountmanager.domain.event.listener;

import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

public interface ProcessingEventListener<Aggregate extends AbstractDomainAggregate<Aggregate>, E extends DomainEvent<Aggregate>> extends DomainEventListener <Aggregate, E> {
    default void beforeStore(E event) {};
    default void afterStore(E event) {};
}
