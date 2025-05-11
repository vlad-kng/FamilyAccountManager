package ru.dorin.familyaccountmanager.family.domain.event;

import ru.dorin.familyaccountmanager.family.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.family.domain.aggregate.FamilyId;

import java.time.Instant;
import java.util.UUID;

public record FamilyBudgetLinkedEvent(
        FamilyId familyId,
        UUID budgetId,
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
        family.linkBudget(budgetId);
    }

    @Override
    public String getDescription() {
        return "family.link.budget";
    }

}
