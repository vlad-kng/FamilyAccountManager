package ru.dorin.familyaccountmanager.domain.aggregate;


import lombok.Getter;
import lombok.Setter;
import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.event.BudgetEvent;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
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

    private final List<BudgetEvent> domainEvents = new ArrayList<>();

    private Budget() {}

    public static Budget recreateFromEvents(List<BudgetEvent> events) {
        Budget budget = new Budget();
        budget.recreateFrom(events);
        return budget;
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
