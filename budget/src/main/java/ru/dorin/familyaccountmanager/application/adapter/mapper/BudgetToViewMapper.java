package ru.dorin.familyaccountmanager.application.adapter.mapper;

import lombok.experimental.UtilityClass;
import ru.dorin.familyaccountmanager.domain.aggregate.Budget;
import ru.dorin.familyaccountmanager.integration.domain.budget.BudgetView;

@UtilityClass
public class BudgetToViewMapper {

    public static BudgetView map(Budget budget) {
        return new BudgetView(
                budget.getId().getId(),
                budget.getFamilyId(),
                budget.getCategory().name(),
                budget.getPeriod(),
                budget.getLimits().amount(),
                budget.getSpent().amount()
        );
    }
}
