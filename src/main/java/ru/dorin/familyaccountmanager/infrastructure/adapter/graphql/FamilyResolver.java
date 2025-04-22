package ru.dorin.familyaccountmanager.infrastructure.adapter.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import ru.dorin.familyaccountmanager.application.port.AccountService;
import ru.dorin.familyaccountmanager.application.port.FamilyService;
import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.family.Family;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;
import ru.dorin.familyaccountmanager.domain.family.Role;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FamilyResolver {
    private final FamilyService familyService;
    private final AccountService accountService;

    @QueryMapping
    public Family getFamily(@Argument UUID id) {
        return familyService.getFamily(new FamilyId(id));
    }

    @MutationMapping
    public FamilyId createFamily(@Argument String surname) {
        return familyService.createFamily(surname);
    }

    @MutationMapping
    public boolean linkAccount(@Argument UUID familyId, @Argument UUID accountId) {
        familyService.linkAccountToFamily(new FamilyId(familyId), new AccountId(accountId));
        return true;
    }

    @MutationMapping
    public boolean addMember(@Argument UUID familyId, @Argument String memberName, @Argument Role role) {
        familyService.addMember(new FamilyId(familyId), memberName, role);
        return true;
    }

    @SchemaMapping(typeName = "Family", field = "accounts")
    public List<Account> getAccounts(Family family) {
        List<AccountId> accountIds = family.getAccountIds();
        return accountService.getAccounts(accountIds);
    }
}
