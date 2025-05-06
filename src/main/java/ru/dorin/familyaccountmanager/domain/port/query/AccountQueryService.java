package ru.dorin.familyaccountmanager.domain.port.query;

import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.account.TransactionDTO;

import java.util.List;

public interface AccountQueryService {
    List<Account> getAccounts(List<AccountId> accountIds);
    Account getAccount(AccountId accountId);
    List<TransactionDTO> getTransactions(AccountId accountId);
}
