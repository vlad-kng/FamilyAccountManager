package ru.dorin.familyaccountmanager.event.account;

import ru.dorin.familyaccountmanager.account.Account;
import ru.dorin.familyaccountmanager.account.AccountId;
import ru.dorin.familyaccountmanager.platform.domain.valueobject.Money;
import ru.dorin.familyaccountmanager.account.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;

public record MoneyDepositedEvent(
        AccountId accountId,
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
        return TransactionType.DEPOSITED;
    }

    @Override
    public String getDescription() {
        return "account.money.deposited";
    }
}
