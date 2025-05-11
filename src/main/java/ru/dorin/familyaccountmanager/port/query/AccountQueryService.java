package ru.dorin.familyaccountmanager.port.query;

import ru.dorin.familyaccountmanager.account.Account;
import ru.dorin.familyaccountmanager.account.AccountId;
import ru.dorin.familyaccountmanager.account.TransactionDTO;

import java.util.List;

public interface AccountQueryService {
    List<Account> getAccounts(List<AccountId> accountIds);
    Account getAccount(AccountId accountId);
    List<TransactionDTO> getTransactions(AccountId accountId);
}
