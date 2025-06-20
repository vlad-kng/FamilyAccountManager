package ru.dorin.familyaccountmanager.domain.valueobject;

import ru.dorin.familyaccountmanager.domain.aggregate.AbstractDomainAggregate;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


public abstract class DomainId<A extends AbstractDomainAggregate<A>> implements Serializable {
    private final UUID id;

    protected DomainId() {
        this.id = UUID.randomUUID();
    }

    protected DomainId(String id) {
        this.id = UUID.fromString(id);
    }

    protected DomainId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DomainId<?> domainId = (DomainId<?>) object;
        return Objects.equals(id, domainId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}