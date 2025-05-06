package ru.dorin.familyaccountmanager.application.adapter.query.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.domain.port.query.BudgetQueryService;
import ru.dorin.familyaccountmanager.domain.port.query.FamilyQueryService;
import ru.dorin.familyaccountmanager.domain.budget.Budget;
import ru.dorin.familyaccountmanager.domain.budget.BudgetId;
import ru.dorin.familyaccountmanager.domain.event.budget.BudgetEvent;
import ru.dorin.familyaccountmanager.domain.family.Family;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;
import ru.dorin.familyaccountmanager.infrastructure.persistence.mongo.MongoEventStore;

import java.util.List;

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

    public List<Budget> getBudgets(FamilyId familyId) {
        Family family = familyQueryService.getFamily(familyId);
        return getBudgets(family.getBudgetIds());
    }

    public List<Budget> getBudgets(List<BudgetId> budgetIds) {
        return eventStore.loadAggregates(budgetIds, Budget::new);
    }
}
