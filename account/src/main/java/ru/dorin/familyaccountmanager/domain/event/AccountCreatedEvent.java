package ru.dorin.familyaccountmanager.domain.event;

import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.dorin.familyaccountmanager.domain.aggregate.Account;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountId;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountName;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountType;

import java.time.Instant;

@JsonTypeName("AccountCreated")
public record AccountCreatedEvent(
        AccountId accountId,
        AccountName accountName,
        AccountType accountType,
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
        account.setAccountType(accountType);
        account.setName(accountName);
    }

    @Override
    public String getDescription() {
        return "account.created";
    }
}
