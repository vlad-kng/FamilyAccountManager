package ru.dorin.familyaccountmanager.domain.event;

import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.DomainId;

import java.time.Instant;

public interface DomainEvent<Entity extends AbstractDomainAggregate<Entity>> {
    DomainId<Entity> getAggregateId();
    Instant occurredAt();
    void applyTo(Entity entity);
    String getDescription();
}
