package ru.dorin.familyaccountmanager.domain.event.family;

import ru.dorin.familyaccountmanager.domain.family.Family;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;
import ru.dorin.familyaccountmanager.domain.family.Member;

import java.time.Instant;

public record MemberAddedEvent(
        FamilyId familyId,
        Member member,
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
        family.addMember(member);
    }

    @Override
    public String getDescription() {
        return "family.add.member";
    }
}
