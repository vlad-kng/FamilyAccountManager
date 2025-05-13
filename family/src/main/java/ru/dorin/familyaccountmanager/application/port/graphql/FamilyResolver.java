package ru.dorin.familyaccountmanager.application.port.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import ru.dorin.familyaccountmanager.application.adapter.mapper.FamilyToViewMapper;
import ru.dorin.familyaccountmanager.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.domain.aggregate.FamilyId;
import ru.dorin.familyaccountmanager.domain.aggregate.Role;
import ru.dorin.familyaccountmanager.domain.port.FamilyQueryService;
import ru.dorin.familyaccountmanager.domain.port.FamilyUseCaseService;
import ru.dorin.familyaccountmanager.integration.domain.account.AccountView;
import ru.dorin.familyaccountmanager.integration.domain.budget.BudgetView;
import ru.dorin.familyaccountmanager.integration.domain.family.FamilyView;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FamilyResolver {
    private final FamilyQueryService familyQueryService;
    private final FamilyUseCaseService familyUseCaseService;

    @QueryMapping
    public FamilyView getFamily(@Argument UUID id) {
        Family family = familyQueryService.getFamily(id);
        return FamilyToViewMapper.map(family);
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
