package ru.dorin.familyaccountmanager.application.adapter.query.inmemory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.domain.aggregate.Budget;
import ru.dorin.familyaccountmanager.domain.aggregate.BudgetId;
import ru.dorin.familyaccountmanager.domain.event.BudgetEvent;
import ru.dorin.familyaccountmanager.integration.domain.budget.BudgetIdQuery;
import ru.dorin.familyaccountmanager.domain.port.BudgetQueryService;
import ru.dorin.familyaccountmanager.domain.port.EventStore;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InMemoryBudgetQueryService implements BudgetQueryService {

    private final EventStore<Budget, BudgetEvent> eventStore;
    private final BudgetIdQuery budgetIdQuery;

    public Budget getBudget(BudgetId budgetId) {
        Budget budget = new Budget(budgetId);
        budget.recreateFrom(eventStore.load(budgetId));
        return budget;
    }

    public List<Budget> getBudgets(UUID familyId) {
        List<UUID> budgetIds = budgetIdQuery.getBudgetIds(familyId);
        return getBudgets(budgetIds.stream().map(BudgetId::new).toList());
    }

    public List<Budget> getBudgets(List<BudgetId> budgetIds) {
        return eventStore.loadAggregates(budgetIds, Budget::new);
    }
}
