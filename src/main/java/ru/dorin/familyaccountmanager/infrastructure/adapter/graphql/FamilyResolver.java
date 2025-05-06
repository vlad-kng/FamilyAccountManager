package ru.dorin.familyaccountmanager.infrastructure.adapter.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.budget.Budget;
import ru.dorin.familyaccountmanager.domain.family.Family;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;
import ru.dorin.familyaccountmanager.domain.family.Role;
import ru.dorin.familyaccountmanager.domain.port.query.AccountQueryService;
import ru.dorin.familyaccountmanager.domain.port.query.BudgetQueryService;
import ru.dorin.familyaccountmanager.domain.port.query.FamilyQueryService;
import ru.dorin.familyaccountmanager.domain.port.usecase.FamilyUseCaseService;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FamilyResolver {
    private final FamilyQueryService familyQueryService;
    private final FamilyUseCaseService familyUseCaseService;
    private final AccountQueryService accountQueryService;
    private final BudgetQueryService budgetQueryService;

    @QueryMapping
    public Family getFamily(@Argument UUID id) {
        return familyQueryService.getFamily(new FamilyId(id));
    }

    @MutationMapping
    public FamilyId createFamily(@Argument String surname) {
        return familyUseCaseService.createFamily(surname);
    }

    @MutationMapping
    public boolean addMember(@Argument UUID familyId, @Argument String memberName, @Argument Role role) {
        familyUseCaseService.addMember(new FamilyId(familyId), memberName, role);
        return true;
    }

    @SchemaMapping(typeName = "Family", field = "accounts")
    public List<Account> getAccounts(Family family) {
        List<AccountId> accountIds = family.getAccountIds();
        return accountQueryService.getAccounts(accountIds);
    }

    @SchemaMapping(typeName = "Family", field = "budgets")
    public List<Budget> getBudgetsForFamily(Family family) {
        return budgetQueryService.getBudgets(family.getId());
    }
}
