package ru.dorin.familyaccountmanager.application.port;

import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.account.AccountType;
import ru.dorin.familyaccountmanager.domain.account.Money;
import ru.dorin.familyaccountmanager.domain.account.TransactionDTO;
import ru.dorin.familyaccountmanager.domain.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    AccountId createAccount(String name, AccountType type, String initialBalance);
    boolean increaseBalance(AccountId accountId, BigDecimal amount);
    boolean withdrawBalance(AccountId accountId, BigDecimal amount, BudgetCategory category);
    void linkAccountToFamily(AccountId accountId, FamilyId familyId);
    Account getAccount(AccountId accountId);
    List<TransactionDTO> getTransactions(AccountId accountId);
    boolean transferMoney(AccountId from, AccountId to, Money money);
    List<Account> getAccounts(List<AccountId> accountIds);
}
