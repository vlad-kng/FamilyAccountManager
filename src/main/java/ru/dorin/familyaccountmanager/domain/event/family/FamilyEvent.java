package ru.dorin.familyaccountmanager.domain.event.family;

import ru.dorin.familyaccountmanager.domain.event.DomainEvent;
import ru.dorin.familyaccountmanager.domain.family.Family;

public interface FamilyEvent extends DomainEvent<Family> {

    @Override
    default Class<Family> getAggregateClass() {
        return Family.class;
    }
}
