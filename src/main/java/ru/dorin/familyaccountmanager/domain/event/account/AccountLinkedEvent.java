package ru.dorin.familyaccountmanager.domain.event.account;

import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.time.Instant;

public record AccountLinkedEvent(
        AccountId accountId,
        FamilyId familyId,
        Instant occurredAt
) implements AccountEvent {
    @Override
    public AccountId getAggregateId() {
        return accountId;
    }

    @Override
    public Instant occurredAt() {
        return occurredAt;
    }

    @Override
    public void applyTo(Account account) {
        account.linkToFamily(familyId);
    }

    @Override
    public String getDescription() {
        return "account.link.family";
    }
}
