package ru.dorin.familyaccountmanager.domain.aggregate;

import lombok.Getter;
import lombok.Setter;
import ru.dorin.familyaccountmanager.domain.event.FamilyEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Family extends AbstractDomainAggregate<Family> {
    private FamilyId id;
    private Surname surname;
    private final List<Member> members = new ArrayList<>();
    private final List<UUID> accountIds = new ArrayList<>();
    private final List<UUID> budgetIds = new ArrayList<>();
    private final List<FamilyEvent> domainEvents = new ArrayList<>();

    private Family() {
    }
    public static Family recreateFromEvents(List<FamilyEvent> events) {
        Family family = new Family();
        family.recreateFrom(events);
        return family;
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
