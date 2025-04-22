package ru.dorin.familyaccountmanager.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.application.port.BudgetService;
import ru.dorin.familyaccountmanager.application.port.EventStore;
import ru.dorin.familyaccountmanager.domain.account.Money;
import ru.dorin.familyaccountmanager.domain.budget.Budget;
import ru.dorin.familyaccountmanager.domain.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.budget.BudgetId;
import ru.dorin.familyaccountmanager.domain.budget.BudgetLimits;
import ru.dorin.familyaccountmanager.domain.event.budget.BudgetEvent;
import ru.dorin.familyaccountmanager.domain.event.budget.BudgetCreatedEvent;
import ru.dorin.familyaccountmanager.domain.event.budget.BudgetSpentEvent;
import ru.dorin.familyaccountmanager.domain.exception.OverBudgetLimitException;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {
    private final EventStore<Budget, BudgetEvent> eventStore;

    @Override
    public BudgetId createBudget(FamilyId familyId, YearMonth period, BudgetLimits limits) {
        BudgetId id = new BudgetId();
        var createdEvent = new BudgetCreatedEvent(id, familyId, period, limits, Instant.now());
        eventStore.append(id, createdEvent);
        return id;
    }

    public boolean spend(BudgetId budgetId, BudgetCategory budgetCategory, BigDecimal amount) {
        Money money = new Money(amount);
        var event = new BudgetSpentEvent(budgetId, budgetCategory, money, Instant.now());
        eventStore.append(budgetId, event);

        Budget budget = getBudget(budgetId);
        if (budget.isOverLimit(budgetCategory)) {
            //TODO send notification, not throw exception
            throw new OverBudgetLimitException(budgetId, budgetCategory);
        }
        return true;
    }

    public Budget getBudget(BudgetId budgetId) {
        Budget budget = new Budget(budgetId);
        return budget.recreateFrom(eventStore.load(budgetId));
    }
}
