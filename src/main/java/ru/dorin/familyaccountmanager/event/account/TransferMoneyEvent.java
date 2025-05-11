package ru.dorin.familyaccountmanager.event.account;

import ru.dorin.familyaccountmanager.account.Account;
import ru.dorin.familyaccountmanager.account.AccountId;
import ru.dorin.familyaccountmanager.platform.domain.valueobject.Money;
import ru.dorin.familyaccountmanager.account.TransactionType;

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
