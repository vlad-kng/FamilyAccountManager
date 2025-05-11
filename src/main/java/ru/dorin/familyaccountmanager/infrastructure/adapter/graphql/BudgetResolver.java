package ru.dorin.familyaccountmanager.infrastructure.adapter.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import ru.dorin.familyaccountmanager.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.budget.BudgetId;
import ru.dorin.familyaccountmanager.family.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.infrastructure.adapter.mapper.BudgetToViewMapper;
import ru.dorin.familyaccountmanager.integration.domain.budget.BudgetView;
import ru.dorin.familyaccountmanager.platform.domain.valueobject.Money;
import ru.dorin.familyaccountmanager.port.query.BudgetQueryService;
import ru.dorin.familyaccountmanager.port.usecase.BudgetUseCaseService;

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
    public List<BudgetView> getBudgetsForFamily(Family family) {
        return budgetQueryService.getBudgets(family.getId().getId())
                .stream().map(BudgetToViewMapper::map).toList();
    }
}
