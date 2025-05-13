package ru.dorin.familyaccountmanager.domain.event;

import ru.dorin.familyaccountmanager.domain.aggregate.Account;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountId;
import ru.dorin.familyaccountmanager.domain.aggregate.TransactionType;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;

import java.math.BigDecimal;
import java.time.Instant;

public record TransferMoneyEvent(
        AccountId from,
        AccountId to,
        Instant occurredAt,
        Money money
) implements AccountTransactionEvent, AccountEvent {
    @Override
    public AccountId getAggregateId() {
        return from;
    }

    @Override
    public Instant occurredAt() {
        return occurredAt;
    }

    @Override
    public void applyTo(Account account) {
        account.withdrawBalance(money);
    }

    @Override
    public BigDecimal getAmount() {
        return money.amount();
    }

    @Override
    public TransactionType getTransactionType() {
        return TransactionType.TRANSFER;
    }

    @Override
    public String getDescription() {
        return "account.transfer.money";
    }
}
