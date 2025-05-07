package ru.dorin.familyaccountmanager.domain.port.usecase;

import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.account.Money;
import ru.dorin.familyaccountmanager.domain.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.math.BigDecimal;

public interface AccountUseCaseService {
    boolean increaseBalance(AccountId accountId, BigDecimal amount);
    boolean withdrawBalance(AccountId accountId, BigDecimal amount, BudgetCategory category);
    void linkAccountToFamily(AccountId accountId, FamilyId familyId);
    boolean transferMoney(AccountId from, AccountId to, Money money);
}
