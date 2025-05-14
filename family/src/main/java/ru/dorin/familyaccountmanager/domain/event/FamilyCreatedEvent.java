package ru.dorin.familyaccountmanager.domain.event;

import ru.dorin.familyaccountmanager.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.domain.aggregate.FamilyId;
import ru.dorin.familyaccountmanager.domain.aggregate.Surname;

import java.time.Instant;

public record FamilyCreatedEvent(
        FamilyId familyId,
        Surname surname,
        Instant occurredAt
) implements FamilyEvent {
    @Override
    public FamilyId getAggregateId() {
        return familyId;
    }

    @Override
    public Instant occurredAt() {
        return occurredAt;
    }

    @Override
    public void applyTo(Family family) {
        family.setId(familyId);
        family.setSurname(surname);
    }

    @Override
    public String getDescription() {
        return "family.created";
    }
}
