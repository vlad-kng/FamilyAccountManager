package ru.dorin.familyaccountmanager.domain.budget;

import ru.dorin.familyaccountmanager.domain.account.Money;

import java.util.HashMap;

public class BudgetSpent extends BudgetValues {
    public BudgetSpent() {
        super(new HashMap<>());
    }

    public void addSpending(BudgetCategory category, Money amount) {
        values.merge(category, amount, Money::add);
    }
}