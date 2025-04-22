package ru.dorin.familyaccountmanager.infrastructure.persistence;

import ru.dorin.familyaccountmanager.application.port.EventStore;
import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.DomainId;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Abstract event store to encapsulate abstract logic of store operations
 * @param <Aggregate>> entity (example: {@link ru.dorin.familyaccountmanager.domain.family.Family}
 * @param <Event> inheritors of {@link ru.dorin.familyaccountmanager.domain.event.DomainEvent}
 */
public abstract class AbstractInMemoryEventStore<
        Aggregate extends AbstractDomainAggregate<Aggregate>,
        Event extends DomainEvent<Aggregate>>
        implements EventStore<Aggregate, Event> {
    private final Map<DomainId<Aggregate>, List<Event>> store = new LinkedHashMap<>();


    public void append(DomainId<Aggregate> aggregateId, Event event) {
        store.computeIfAbsent(aggregateId, id -> new ArrayList<>()).add(event);
    }

    public List<Event> load(DomainId<Aggregate> aggregateId) {
        return store.getOrDefault(aggregateId, Collections.emptyList());
    }

    public Map<DomainId<Aggregate>, List<Event>> loadAll(Collection<? extends DomainId<Aggregate>> ids) {
        return ids.stream().distinct()
                .collect(Collectors.toMap(Function.identity(), this::load));
    }

    public List<Aggregate> loadAggregates(Collection<? extends DomainId<Aggregate>> ids, Function<DomainId<Aggregate>, Aggregate> constructor) {
        return loadAll(ids).entrySet().stream()
                .map(entry -> {
                    Aggregate aggregate = constructor.apply(entry.getKey());
                    return aggregate.recreateFrom(entry.getValue());
                })
                .toList();
    }
}
