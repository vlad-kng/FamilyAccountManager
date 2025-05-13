package ru.dorin.familyaccountmanager.domain.port;

import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.DomainId;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract event store to encapsulate abstract logic of store operations
 * @param <Aggregate>> entity extends of {@link AbstractDomainAggregate}
 * @param <Event> inheritors of {@link DomainEvent}
 */
public abstract class AbstractInMemoryEventStore<
        Aggregate extends AbstractDomainAggregate<Aggregate>,
        Event extends DomainEvent<Aggregate>>
        implements EventStore<Aggregate, Event> {
    private final Map<DomainId<Aggregate>, List<Event>> store = new LinkedHashMap<>();


    public void append(Event event) {
        store.computeIfAbsent(event.getAggregateId(), id -> new ArrayList<>()).add(event);
    }

    public List<Event> load(DomainId<Aggregate> aggregateId) {
        return store.getOrDefault(aggregateId, Collections.emptyList());
    }

    @Override
    public boolean contains(DomainId<Aggregate> aggregateId) {
        return store.containsKey(aggregateId);
    }
}
