package ru.dorin.familyaccountmanager.domain;

import ru.dorin.familyaccountmanager.domain.event.DomainEvent;
import ru.dorin.familyaccountmanager.domain.exception.EmptyEventsException;

import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractDomainAggregate<T extends AbstractDomainAggregate<T>> {

    protected void apply(DomainEvent<T> domainEvent) {
        T entity = (T) this;
        domainEvent.applyTo(entity);
    }

    public abstract DomainId<T> getId();

    public T recreateFrom(List<? extends DomainEvent<T>> domainEvents) {
        if (domainEvents.isEmpty()) {
            throw new EmptyEventsException(getId());
        }
        domainEvents.forEach(this::apply);
        return (T) this;
    }
}
