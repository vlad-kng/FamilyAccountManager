package ru.dorin.familyaccountmanager.domain.listener;

import ru.dorin.familyaccountmanager.domain.port.EventStore;
import ru.dorin.familyaccountmanager.domain.aggregate.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

public abstract class AbstractStoringEventListener<Aggregate extends AbstractDomainAggregate<Aggregate>, E extends DomainEvent<Aggregate>> implements DomainEventListener<Aggregate, DomainEvent<Aggregate>> {
    private final EventStore<Aggregate, E> eventStore;

    protected AbstractStoringEventListener(EventStore<Aggregate, E> eventStore) {
        this.eventStore = eventStore;
    }

    public void store(E event) {
        eventStore.append(event);
    }
}
