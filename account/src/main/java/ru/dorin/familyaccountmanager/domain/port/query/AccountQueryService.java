package ru.dorin.familyaccountmanager.domain.port.query;

import ru.dorin.familyaccountmanager.domain.aggregate.Account;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountId;
import ru.dorin.familyaccountmanager.domain.aggregate.TransactionDTO;

import java.util.List;

public interface AccountQueryService {
    List<Account> getAccounts(List<AccountId> accountIds);
    Account getAccount(AccountId accountId);
    List<TransactionDTO> getTransactions(AccountId accountId);
}
