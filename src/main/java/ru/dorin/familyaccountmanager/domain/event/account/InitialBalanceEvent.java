package ru.dorin.familyaccountmanager.domain.event.account;

import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.account.Money;
import ru.dorin.familyaccountmanager.domain.account.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;

public record InitialBalanceEvent(
        AccountId accountId,
        Money money,
        Instant occurredAt
) implements AccountTransactionEvent {


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
        return TransactionType.ACCOUNT_INITIAL_DEPOSIT;
    }

    @Override
    public String getDescription() {
        return "account.initial.deposit";
    }
}
