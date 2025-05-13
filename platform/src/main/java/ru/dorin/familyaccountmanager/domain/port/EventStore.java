package ru.dorin.familyaccountmanager.domain.port;

import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.DomainId;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface EventStore<
        Aggregate extends AbstractDomainAggregate<Aggregate>,
        Event extends DomainEvent<Aggregate>>{

    void append(Event event);

    List<Event> load(DomainId<Aggregate> aggregateId);


    default List<Aggregate> loadAggregates(Collection<? extends DomainId<Aggregate>> domainIds,
                                           Function<List<Event>, Aggregate> constructor) {
        return loadAll(domainIds).values().stream()
                .map(constructor)
                .toList();
    }

    default Map<DomainId<Aggregate>, List<Event>> loadAll(Collection<? extends DomainId<Aggregate>> ids) {
        return ids.stream().distinct()
                .collect(Collectors.toMap(Function.identity(), this::load));
    }

    boolean contains(DomainId<Aggregate> aggregateId);
}
