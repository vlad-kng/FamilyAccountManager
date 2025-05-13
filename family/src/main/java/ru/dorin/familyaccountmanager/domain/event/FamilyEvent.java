package ru.dorin.familyaccountmanager.domain.event;

import ru.dorin.familyaccountmanager.domain.aggregate.Family;

public interface FamilyEvent extends DomainEvent<Family> {

    @Override
    default Class<Family> getAggregateClass() {
        return Family.class;
    }
}
