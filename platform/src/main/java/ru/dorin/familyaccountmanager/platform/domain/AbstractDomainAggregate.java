package ru.dorin.familyaccountmanager.platform.domain;

import ru.dorin.familyaccountmanager.platform.domain.event.DomainEvent;
import ru.dorin.familyaccountmanager.platform.domain.exception.EmptyEventsException;

import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractDomainAggregate<T extends AbstractDomainAggregate<T>> {

    protected void apply(DomainEvent<T> domainEvent) {
        T entity = (T) this;
        domainEvent.applyTo(entity);
    }

    public abstract DomainId<T> getId();

    public void recreateFrom(List<? extends DomainEvent<T>> domainEvents) {
        if (domainEvents.isEmpty()) {
            throw new EmptyEventsException(getId());
        }
        domainEvents.forEach(this::apply);
    }
}
