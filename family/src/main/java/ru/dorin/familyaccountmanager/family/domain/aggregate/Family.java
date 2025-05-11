package ru.dorin.familyaccountmanager.family.domain.aggregate;

import lombok.Getter;
import lombok.Setter;
import ru.dorin.familyaccountmanager.platform.domain.AbstractDomainAggregate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Family extends AbstractDomainAggregate<Family> {
    private final FamilyId id;
    private Surname surname;
    private final List<Member> members = new ArrayList<>();
    private final List<UUID> accountIds = new ArrayList<>();
    private final List<UUID> budgetIds = new ArrayList<>();

    public Family(FamilyId id) {
        this.id = id;
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void linkAccount(UUID accountId) {
        accountIds.add(accountId);
    }

    public void linkBudget(UUID budgetId) {
        budgetIds.add(budgetId);
    }

    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public List<UUID> getAccountIds() {
        return Collections.unmodifiableList(accountIds);
    }
    public List<UUID> getBudgetIds() {
        return Collections.unmodifiableList(budgetIds);
    }
}
