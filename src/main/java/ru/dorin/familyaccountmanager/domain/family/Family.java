package ru.dorin.familyaccountmanager.domain.family;

import lombok.Getter;
import lombok.Setter;
import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.account.AccountId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class Family extends AbstractDomainAggregate<Family> {
    private final FamilyId id;
    private Surname surname;
    private final List<Member> members = new ArrayList<>();
    private final List<AccountId> accountIds = new ArrayList<>();

    public Family(FamilyId id) {
        this.id = id;
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void linkAccount(AccountId accountId) {
        accountIds.add(accountId);
    }

    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public List<AccountId> getAccountIds() {
        return Collections.unmodifiableList(accountIds);
    }
}
