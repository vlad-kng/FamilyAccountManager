package ru.dorin.familyaccountmanager.port.usecase;

import ru.dorin.familyaccountmanager.account.AccountId;
import ru.dorin.familyaccountmanager.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.platform.domain.valueobject.Money;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountUseCaseService {
    boolean increaseBalance(AccountId accountId, BigDecimal amount);
    boolean withdrawBalance(AccountId accountId, BigDecimal amount, BudgetCategory category);
    void linkAccountToFamily(UUID accountId, UUID familyId);
    boolean transferMoney(AccountId from, AccountId to, Money money);
}
