package ru.dorin.familyaccountmanager.budget;

import lombok.Getter;
import lombok.Setter;
import ru.dorin.familyaccountmanager.platform.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.platform.domain.DomainId;
import ru.dorin.familyaccountmanager.platform.domain.valueobject.Money;

import java.time.YearMonth;
import java.util.UUID;

@Getter
@Setter
public class Budget extends AbstractDomainAggregate<Budget> {

    private BudgetId id;

    private UUID familyId;

    private BudgetCategory category;

    private YearMonth period;

    private Money limits = Money.zero();

    private Money spent = Money.zero();

    public Budget(BudgetId id) {
        this.id = id;
    }

    public Budget(DomainId<Budget> budgetDomainId) {
        this.id = (BudgetId) budgetDomainId;
    }

    public void spend(Money money) {
        spent = spent.add(money);
    }

    public boolean isOverLimit() {
        return spent.isGreaterThan(limits);
    }
    public boolean isOverLimit(Money spent) {
        return this.spent.add(spent).isGreaterThan(limits);
    }

    public boolean similarCategories(BudgetCategory budgetCategory) {
        return category.equals(budgetCategory);
    }
}
