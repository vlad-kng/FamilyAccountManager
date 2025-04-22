package ru.dorin.familyaccountmanager.application;

import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.family.Family;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;
import ru.dorin.familyaccountmanager.domain.family.Role;

public interface FamilyService {
    FamilyId createFamily(String surname);
    void addMember(FamilyId familyId, String memberName, Role role);
    void linkAccountToFamily(FamilyId familyId, AccountId accountId);
    Family getFamily(FamilyId familyId);
}
