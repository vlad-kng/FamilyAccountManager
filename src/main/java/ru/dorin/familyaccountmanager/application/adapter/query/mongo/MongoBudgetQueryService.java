package ru.dorin.familyaccountmanager.application.adapter.query.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.family.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.family.domain.aggregate.FamilyId;
import ru.dorin.familyaccountmanager.port.query.BudgetQueryService;
import ru.dorin.familyaccountmanager.family.domain.port.FamilyQueryService;
import ru.dorin.familyaccountmanager.budget.Budget;
import ru.dorin.familyaccountmanager.budget.BudgetId;
import ru.dorin.familyaccountmanager.event.budget.BudgetEvent;
import ru.dorin.familyaccountmanager.infrastructure.persistence.mongo.MongoEventStore;

import java.util.List;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class MongoBudgetQueryService implements BudgetQueryService {

    private final MongoEventStore<Budget, BudgetEvent> eventStore;
    private final FamilyQueryService familyQueryService;

    public Budget getBudget(BudgetId budgetId) {
        Budget budget = new Budget(budgetId);
        budget.recreateFrom(eventStore.load(budgetId));
        return budget;
    }

    @Override
    public List<Budget> getBudgets(UUID familyId) {
        return getBudgets(new FamilyId(familyId));
    }

    public List<Budget> getBudgets(FamilyId familyId) {
        Family family = familyQueryService.getFamily(familyId.getId());
        return getBudgets(family.getBudgetIds().stream().map(BudgetId::new).toList());
    }

    public List<Budget> getBudgets(List<BudgetId> budgetIds) {
        return eventStore.loadAggregates(budgetIds, Budget::new);
    }
}
