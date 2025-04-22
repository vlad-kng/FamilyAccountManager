package ru.dorin.familyaccountmanager.domain.event.family;

import ru.dorin.familyaccountmanager.domain.family.Family;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;
import ru.dorin.familyaccountmanager.domain.family.Surname;

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
