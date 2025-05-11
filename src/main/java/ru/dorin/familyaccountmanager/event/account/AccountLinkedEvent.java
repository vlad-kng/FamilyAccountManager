package ru.dorin.familyaccountmanager.event.account;

import ru.dorin.familyaccountmanager.account.Account;
import ru.dorin.familyaccountmanager.account.AccountId;

import java.time.Instant;
import java.util.UUID;

public record AccountLinkedEvent(
        AccountId accountId,
        UUID familyId,
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
