package ru.dorin.familyaccountmanager.domain.budget;

import ru.dorin.familyaccountmanager.domain.account.Money;

import java.util.Map;

public class BudgetLimits extends BudgetValues {
    public BudgetLimits(Map<BudgetCategory, Money> limits) {
        super(limits);
    }
}