package ru.dorin.familyaccountmanager.domain.budget;

import lombok.Getter;
import lombok.Setter;
import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.account.Money;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.time.YearMonth;

@Getter
@Setter
public class Budget extends AbstractDomainAggregate<Budget> {

    private final BudgetId id;

    private FamilyId familyId;

    private YearMonth period;

    private BudgetLimits limits;

    private BudgetSpent spent;

    public Budget(BudgetId id) {
        this.id = id;
    }

    public void spend(BudgetCategory category, Money money) {
        spent.addSpending(category, money);
    }

    public boolean isOverLimit(BudgetCategory category) {
        Money limits = this.limits.get(category);
        Money spent = this.spent.get(category);
        return spent.isGreaterThan(limits);
    }
}
