package ru.dorin.familyaccountmanager.infrastructure.adapter.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import ru.dorin.familyaccountmanager.application.port.BudgetService;
import ru.dorin.familyaccountmanager.domain.account.Money;
import ru.dorin.familyaccountmanager.domain.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.budget.BudgetId;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BudgetResolver {
    private final BudgetService budgetService;


    @MutationMapping
    public BudgetId createBudget(@Argument UUID familyId,
                                  @Argument BudgetCategory category,
                                  @Argument YearMonth period,
                                  @Argument String limits) {
        return budgetService.createBudget(
                new FamilyId(familyId), category, period, new Money(new BigDecimal(limits))
        );
    }

}
