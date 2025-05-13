package ru.dorin.familyaccountmanager.application.port.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import ru.dorin.familyaccountmanager.application.adapter.mapper.BudgetToViewMapper;
import ru.dorin.familyaccountmanager.domain.aggregate.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.aggregate.BudgetId;
import ru.dorin.familyaccountmanager.integration.domain.budget.BudgetView;
import ru.dorin.familyaccountmanager.integration.domain.family.FamilyView;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;
import ru.dorin.familyaccountmanager.domain.port.BudgetQueryService;
import ru.dorin.familyaccountmanager.domain.port.BudgetUseCaseService;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BudgetResolver {
    private final BudgetUseCaseService budgetService;
    private final BudgetQueryService budgetQueryService;

    @MutationMapping
    public BudgetId createBudget(@Argument UUID familyId,
                                  @Argument BudgetCategory category,
                                  @Argument YearMonth period,
                                  @Argument String limits) {
        return budgetService.createBudget(
                familyId, category, period, new Money(new BigDecimal(limits))
        );
    }

    @SchemaMapping(typeName = "Family", field = "budgets")
    public List<BudgetView> getBudgetsForFamily(FamilyView family) {
        return budgetQueryService.getBudgets(family.budgetIds().stream().map(BudgetId::new).toList())
                .stream().map(BudgetToViewMapper::map).toList();
    }
}
