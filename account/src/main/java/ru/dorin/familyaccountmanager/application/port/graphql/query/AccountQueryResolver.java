package ru.dorin.familyaccountmanager.application.port.graphql.query;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import ru.dorin.familyaccountmanager.application.adapter.mapper.AccountToViewMapper;
import ru.dorin.familyaccountmanager.domain.aggregate.Account;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountId;
import ru.dorin.familyaccountmanager.domain.aggregate.TransactionDTO;
import ru.dorin.familyaccountmanager.domain.port.query.AccountQueryService;
import ru.dorin.familyaccountmanager.integration.domain.account.AccountView;
import ru.dorin.familyaccountmanager.integration.domain.family.FamilyView;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AccountQueryResolver {
    private final AccountQueryService accountQueryService;

    @QueryMapping
    public AccountView getAccount(@Argument UUID id) {
        Account account = accountQueryService.getAccount(new AccountId(id));
        return AccountToViewMapper.map(account);
    }

    @QueryMapping
    public List<TransactionDTO> getTransactions(@Argument UUID accountId) {
        return accountQueryService.getTransactions(new AccountId(accountId));
    }

    @SchemaMapping(typeName = "Account", field = "transactions")
    public List<TransactionDTO> getTransactionsForAccount(AccountView account) {
        return accountQueryService.getTransactions(new AccountId(account.id()));
    }

    @SchemaMapping(typeName = "Family", field = "accounts")
    public List<AccountView> getAccounts(FamilyView family) {
        List<UUID> accountIds = family.accountIds();
        return accountQueryService.getAccounts(accountIds.stream().map(AccountId::new).toList())
                .stream().map(AccountToViewMapper::map).toList();
    }
}