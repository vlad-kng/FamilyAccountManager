package ru.dorin.familyaccountmanager.domain.event.account;

import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.account.Money;
import ru.dorin.familyaccountmanager.domain.account.TransactionType;
import ru.dorin.familyaccountmanager.domain.budget.BudgetCategory;

import java.math.BigDecimal;
import java.time.Instant;

public record MoneyWithdrawalEvent(
        AccountId accountId,
        BudgetCategory category,
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
        account.withdrawBalance(money);
    }

    @Override
    public BigDecimal getAmount() {
        return money.amount();
    }

    @Override
    public TransactionType getTransactionType() {
        return TransactionType.WITHDRAWAL;
    }

    @Override
    public String getDescription() {
        return "account.money.withdrawal";
    }
}
