package ru.dorin.familyaccountmanager.domain.event.family;

import ru.dorin.familyaccountmanager.domain.budget.BudgetId;
import ru.dorin.familyaccountmanager.domain.family.Family;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.time.Instant;

public record FamilyBudgetLinkedEvent(
        FamilyId familyId,
        BudgetId budgetId,
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
