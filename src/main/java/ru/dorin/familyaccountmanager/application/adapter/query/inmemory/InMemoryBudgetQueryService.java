package ru.dorin.familyaccountmanager.application.adapter.query.inmemory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.family.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.family.domain.port.FamilyQueryService;
import ru.dorin.familyaccountmanager.budget.Budget;
import ru.dorin.familyaccountmanager.budget.BudgetId;
import ru.dorin.familyaccountmanager.event.budget.BudgetEvent;
import ru.dorin.familyaccountmanager.port.query.BudgetQueryService;
import ru.dorin.familyaccountmanager.platform.domain.port.EventStore;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InMemoryBudgetQueryService implements BudgetQueryService {

    private final EventStore<Budget, BudgetEvent> eventStore;
    private final FamilyQueryService familyQueryService;

    public Budget getBudget(BudgetId budgetId) {
        Budget budget = new Budget(budgetId);
        budget.recreateFrom(eventStore.load(budgetId));
        return budget;
    }

    public List<Budget> getBudgets(UUID familyId) {
        Family family = familyQueryService.getFamily(familyId);
        return getBudgets(family.getBudgetIds().stream().map(BudgetId::new).toList());
    }

    public List<Budget> getBudgets(List<BudgetId> budgetIds) {
        return eventStore.loadAggregates(budgetIds, Budget::new);
    }
}
