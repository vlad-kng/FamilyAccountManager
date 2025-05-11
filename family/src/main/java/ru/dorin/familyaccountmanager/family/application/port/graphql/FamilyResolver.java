package ru.dorin.familyaccountmanager.family.application.port.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import ru.dorin.familyaccountmanager.family.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.family.domain.aggregate.FamilyId;
import ru.dorin.familyaccountmanager.family.domain.aggregate.Role;
import ru.dorin.familyaccountmanager.family.domain.port.FamilyQueryService;
import ru.dorin.familyaccountmanager.family.domain.port.FamilyUseCaseService;
import ru.dorin.familyaccountmanager.integration.domain.account.AccountView;
import ru.dorin.familyaccountmanager.integration.domain.budget.BudgetView;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FamilyResolver {
    private final FamilyQueryService familyQueryService;
    private final FamilyUseCaseService familyUseCaseService;

    @QueryMapping
    public Family getFamily(@Argument UUID id) {
        return familyQueryService.getFamily(id);
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

    @SchemaMapping(typeName = "Account", field = "family")
    public Family getFamilyForAccount(AccountView account) {
        return familyQueryService.getFamily(account.familyId());
    }

    @SchemaMapping(typeName = "Budget", field = "family")
    public Family getFamilyForBudget(BudgetView budget) {
        return familyQueryService.getFamily(budget.familyId());
    }
}
