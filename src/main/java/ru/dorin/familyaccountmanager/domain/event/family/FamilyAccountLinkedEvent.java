package ru.dorin.familyaccountmanager.domain.event.family;

import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.family.Family;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.time.Instant;

public record FamilyAccountLinkedEvent(
        FamilyId familyId,
        AccountId accountId,
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
        family.linkAccount(accountId);
    }

    @Override
    public String getDescription() {
        return "family.link.account";
    }

}
