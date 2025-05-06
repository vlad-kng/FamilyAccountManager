package ru.dorin.familyaccountmanager.domain.port.usecase;

import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.budget.BudgetId;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;
import ru.dorin.familyaccountmanager.domain.family.Role;

public interface FamilyUseCaseService {
    FamilyId createFamily(String surname);
    void addMember(FamilyId familyId, String memberName, Role role);
    void linkAccountToFamily(FamilyId familyId, AccountId accountId);
    void addBudget(FamilyId familyId, BudgetId budgetId);
}
