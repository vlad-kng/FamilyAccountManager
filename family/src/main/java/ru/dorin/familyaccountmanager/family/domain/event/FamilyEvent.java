package ru.dorin.familyaccountmanager.family.domain.event;

import ru.dorin.familyaccountmanager.family.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.platform.domain.event.DomainEvent;

public interface FamilyEvent extends DomainEvent<Family> {

    @Override
    default Class<Family> getAggregateClass() {
        return Family.class;
    }
}
