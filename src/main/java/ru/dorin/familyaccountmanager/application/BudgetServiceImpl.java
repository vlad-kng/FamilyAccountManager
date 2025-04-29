package ru.dorin.familyaccountmanager.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.application.port.BudgetService;
import ru.dorin.familyaccountmanager.application.port.EventStore;
import ru.dorin.familyaccountmanager.application.port.FamilyService;
import ru.dorin.familyaccountmanager.domain.account.Money;
import ru.dorin.familyaccountmanager.domain.budget.Budget;
import ru.dorin.familyaccountmanager.domain.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.budget.BudgetId;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;
import ru.dorin.familyaccountmanager.domain.event.budget.BudgetCreatedEvent;
import ru.dorin.familyaccountmanager.domain.event.budget.BudgetSpentEvent;
import ru.dorin.familyaccountmanager.domain.event.publisher.DomainEventPublisher;
import ru.dorin.familyaccountmanager.domain.exception.BudgetAlreadyCreatedException;
import ru.dorin.familyaccountmanager.domain.exception.OverBudgetLimitException;
import ru.dorin.familyaccountmanager.domain.family.Family;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {
    private final EventStore<Budget, DomainEvent<Budget>> eventStore;
    private final FamilyService familyService;
    private final DomainEventPublisher publisher;

    @Override
    public BudgetId createBudget(FamilyId familyId, BudgetCategory category, YearMonth period, Money limits) {
        getBudgets(familyId)
                .stream()
                .filter(budget -> budget.similarCategories(category))
                .filter(budget -> period.equals(budget.getPeriod()))
                .findAny()
                .ifPresent(budget -> {
                    throw new BudgetAlreadyCreatedException(budget.getFamilyId(), budget.getCategory());
                });
        BudgetId id = new BudgetId();
        var createdEvent = new BudgetCreatedEvent(id, familyId, category, period, limits, Instant.now());
        publisher.publish(createdEvent);
        return id;
    }

    public void spend(FamilyId familyId, BudgetCategory category, Money money, Instant occurredAt) {
        getBudgets(familyId)
                .stream()
                .filter(budget -> budget.similarCategories(category))
                .filter(budget -> YearMonth.from(occurredAt.atZone(ZoneId.systemDefault())).equals(budget.getPeriod()))
                .findAny()
                .ifPresent(budget -> {
                    var id = budget.getId();
                    var event = new BudgetSpentEvent(id, category, money, Instant.now());
                    eventStore.append(id, event);
                    if(budget.isOverLimit(money)) {
                        throw new OverBudgetLimitException(budget.getId(), category);
                    }
                });
    }

    public Budget getBudget(BudgetId budgetId) {
        Budget budget = new Budget(budgetId);
        budget.recreateFrom(eventStore.load(budgetId));
        return budget;
    }

    private List<Budget> getBudgets(FamilyId familyId) {
        Family family = familyService.getFamily(familyId);
        return getBudgets(family.getBudgetIds());
    }

    public List<Budget> getBudgets(List<BudgetId> budgetIds) {
        return eventStore.loadAggregates(budgetIds, Budget::new);
    }
}
