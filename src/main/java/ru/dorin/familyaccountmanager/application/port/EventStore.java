package ru.dorin.familyaccountmanager.application.port;

import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.DomainId;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public interface EventStore<
        Aggregate extends AbstractDomainAggregate<Aggregate>,
        Event extends DomainEvent<Aggregate>>{

    void append(DomainId<Aggregate> aggregateId, Event event);

    List<Event> load(DomainId<Aggregate> aggregateId);

    List<Aggregate> loadAggregates(Collection<? extends DomainId<Aggregate>> ids, Function<DomainId<Aggregate>, Aggregate> constructor);

    boolean contains(DomainId<Aggregate> aggregateId);
}
