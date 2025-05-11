package ru.dorin.familyaccountmanager.family.domain.event;

import ru.dorin.familyaccountmanager.family.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.family.domain.aggregate.FamilyId;
import ru.dorin.familyaccountmanager.family.domain.aggregate.Member;

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
