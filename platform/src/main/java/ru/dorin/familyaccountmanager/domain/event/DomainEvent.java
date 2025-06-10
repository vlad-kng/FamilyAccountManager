package ru.dorin.familyaccountmanager.domain.event;

import ru.dorin.familyaccountmanager.domain.aggregate.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.aggregate.DomainId;

import java.time.Instant;

public interface DomainEvent<Entity extends AbstractDomainAggregate<Entity>> {
    DomainId<Entity> getAggregateId();
    Class<Entity> getAggregateClass();
    Instant occurredAt();
    void applyTo(Entity entity);
    String getDescription();

    default Object[] getDescriptionArgs() {
        return new Object[]{getAggregateId()};
    }

    default String getEventName() {
        return getClass().getSimpleName();
    }
}
