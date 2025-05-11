package ru.dorin.familyaccountmanager.family.domain.event;

import ru.dorin.familyaccountmanager.family.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.family.domain.aggregate.FamilyId;
import ru.dorin.familyaccountmanager.family.domain.aggregate.Surname;

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
        family.setSurname(surname);
    }

    @Override
    public String getDescription() {
        return "family.created";
    }
}
