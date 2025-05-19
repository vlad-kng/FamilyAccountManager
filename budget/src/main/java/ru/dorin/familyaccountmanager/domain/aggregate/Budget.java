package ru.dorin.familyaccountmanager.domain.aggregate;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.dorin.familyaccountmanager.domain.event.BudgetCreatedEvent;
import ru.dorin.familyaccountmanager.domain.event.BudgetEvent;
import ru.dorin.familyaccountmanager.domain.event.BudgetSpentEvent;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;

import java.time.Instant;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Slf4j
public class Budget extends AbstractDomainAggregate<Budget> {

    private BudgetId id;

    private UUID familyId;

    private BudgetCategory category;

    private YearMonth period;

    private Money limits = Money.zero();

    private Money spent = Money.zero();

    private final List<BudgetEvent> domainEvents = new ArrayList<>();

    private Budget() {}

    private Budget(UUID familyId, BudgetCategory category, YearMonth period, Money limits) {
        this.id = new BudgetId();
        this.familyId = familyId;
        this.category = category;
        this.period = period;
        this.limits = limits;
        domainEvents.add(new BudgetCreatedEvent(id, familyId, category, period, limits, Instant.now()));
    }

    public static Budget create(UUID familyId, BudgetCategory category, YearMonth period, Money limits) {
        return new Budget(familyId, category, period, limits);
    }

    public static Budget recreateFromEvents(List<BudgetEvent> events) {
        Budget budget = new Budget();
        budget.recreateFrom(events);
        return budget;
    }

    public void spend(Money money) {
        addSpent(money);
        domainEvents.add(new BudgetSpentEvent(id, category, money, Instant.now()));
    }

    public void addSpent(Money money) {
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
