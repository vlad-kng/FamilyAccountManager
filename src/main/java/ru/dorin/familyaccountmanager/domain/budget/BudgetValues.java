package ru.dorin.familyaccountmanager.domain.budget;

import ru.dorin.familyaccountmanager.domain.account.Money;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class BudgetValues {
    protected final Map<BudgetCategory, Money> values;

    protected BudgetValues(Map<BudgetCategory, Money> values) {
        this.values = new HashMap<>(values);
    }

    public Money get(BudgetCategory category) {
        return values.getOrDefault(category, Money.zero());
    }

    public Map<BudgetCategory, Money> asMap() {
        return Collections.unmodifiableMap(values);
    }
}