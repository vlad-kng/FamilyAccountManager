package ru.dorin.familyaccountmanager.family.domain.port;


import ru.dorin.familyaccountmanager.family.domain.aggregate.FamilyId;
import ru.dorin.familyaccountmanager.family.domain.aggregate.Role;

import java.util.UUID;

public interface FamilyUseCaseService {
    FamilyId createFamily(String surname);
    void addMember(FamilyId familyId, String memberName, Role role);
    void linkAccountToFamily(FamilyId familyId, UUID accountId);
    void addBudget(FamilyId familyId, UUID budgetId);
}
