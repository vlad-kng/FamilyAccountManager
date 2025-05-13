package ru.dorin.familyaccountmanager.application.adapter.query.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.domain.aggregate.Budget;
import ru.dorin.familyaccountmanager.domain.aggregate.BudgetId;
import ru.dorin.familyaccountmanager.domain.port.BudgetQueryService;
import ru.dorin.familyaccountmanager.domain.event.BudgetEvent;
import ru.dorin.familyaccountmanager.integration.domain.budget.BudgetIdQuery;
import ru.dorin.familyaccountmanager.domain.port.EventStore;

import java.util.List;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class MongoBudgetQueryService implements BudgetQueryService {

    private final EventStore<Budget, BudgetEvent> eventStore;
    private final BudgetIdQuery budgetIdQuery;

    public Budget getBudget(BudgetId budgetId) {
        return Budget.recreateFromEvents(eventStore.load(budgetId));
    }

    @Override
    public List<Budget> getBudgets(UUID familyId) {
        List<UUID> budgetIds = budgetIdQuery.getBudgetIds(familyId);
        return getBudgets(budgetIds.stream().map(BudgetId::new).toList());
    }

    public List<Budget> getBudgets(List<BudgetId> budgetIds) {
        return eventStore.loadAggregates(budgetIds, Budget::recreateFromEvents);
    }
}
