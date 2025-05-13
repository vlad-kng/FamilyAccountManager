package ru.dorin.familyaccountmanager.domain.event;

import ru.dorin.familyaccountmanager.domain.aggregate.Account;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountId;
import ru.dorin.familyaccountmanager.domain.aggregate.TransactionType;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;

import java.math.BigDecimal;
import java.time.Instant;

public record MoneyTransferReceivedEvent(
        AccountId accountId,
        AccountId from,
        Instant occurredAt,
        Money money
) implements AccountTransactionEvent, AccountEvent {
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
        account.increaseBalance(money);
    }

    @Override
    public BigDecimal getAmount() {
        return money.amount();
    }

    @Override
    public TransactionType getTransactionType() {
        return TransactionType.RECEIVED_TRANSFER;
    }

    @Override
    public String getDescription() {
        return "account.money.transfer.received";
    }
}
