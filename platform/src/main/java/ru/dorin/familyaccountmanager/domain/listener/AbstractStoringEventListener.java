package ru.dorin.familyaccountmanager.domain.listener;

import lombok.RequiredArgsConstructor;
import ru.dorin.familyaccountmanager.domain.port.EventStore;
import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

@RequiredArgsConstructor
public abstract class AbstractStoringEventListener<Aggregate extends AbstractDomainAggregate<Aggregate>, E extends DomainEvent<Aggregate>> implements DomainEventListener<Aggregate, DomainEvent<Aggregate>> {
    private final EventStore<Aggregate, E> eventStore;

    public void store(E event) {
        eventStore.append(event);
    }
}
