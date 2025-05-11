package ru.dorin.familyaccountmanager.platform.domain.event;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.dorin.familyaccountmanager.platform.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.platform.domain.DomainId;

import java.time.Instant;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@eventType")
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
