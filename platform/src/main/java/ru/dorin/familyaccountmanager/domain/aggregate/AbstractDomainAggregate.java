package ru.dorin.familyaccountmanager.domain.aggregate;

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

    public abstract<E extends DomainEvent<T>> List<E> getDomainEvents();

    public void recreateFrom(List<? extends DomainEvent<T>> domainEvents) {
        if (domainEvents.isEmpty()) {
            throw new EmptyEventsException(getId());
        }
        domainEvents.forEach(this::apply);
    }

    public <E extends DomainEvent<T>>List<E> pullDomainEvent() {
        var domainEvents = getDomainEvents();
        var copy = List.copyOf(domainEvents);
        domainEvents.clear();
        return (List<E>) copy;
    }
}
