package ru.dorin.familyaccountmanager.domain.event.listener;

import lombok.RequiredArgsConstructor;
import ru.dorin.familyaccountmanager.application.port.EventStore;
import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

@RequiredArgsConstructor
public abstract class AbstractStoringEventListener<Aggregate extends AbstractDomainAggregate<Aggregate>> implements DomainEventListener<Aggregate, DomainEvent<Aggregate>> {
    private final EventStore<Aggregate, DomainEvent<Aggregate>> eventStore;

    public void store(DomainEvent<Aggregate> event) {
        eventStore.append(event.getAggregateId(), event);
    }
}
