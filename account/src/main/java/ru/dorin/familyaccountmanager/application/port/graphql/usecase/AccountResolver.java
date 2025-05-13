package ru.dorin.familyaccountmanager.application.port.graphql.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import ru.dorin.familyaccountmanager.application.adapter.mapper.AccountToViewMapper;
import ru.dorin.familyaccountmanager.domain.aggregate.Account;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountId;
import ru.dorin.familyaccountmanager.domain.aggregate.TransactionDTO;
import ru.dorin.familyaccountmanager.domain.port.query.AccountQueryService;
import ru.dorin.familyaccountmanager.domain.port.usecase.AccountUseCaseService;
import ru.dorin.familyaccountmanager.integration.domain.account.AccountView;
import ru.dorin.familyaccountmanager.integration.domain.family.FamilyView;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AccountResolver {
    private final AccountUseCaseService accountUseCaseService;
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

    @MutationMapping
    public boolean increaseBalance(@Argument UUID id,
                                   @Argument String amount) {
        return accountUseCaseService.increaseBalance(new AccountId(id), new BigDecimal(amount));
    }

    @MutationMapping
    public boolean withdrawBalance(@Argument UUID id,
                                   @Argument String amount,
                                   @Argument String category) {
        return accountUseCaseService.withdrawBalance(new AccountId(id), new BigDecimal(amount), category);
    }

    @MutationMapping
    public boolean linkAccount(@Argument UUID familyId, @Argument UUID accountId) {
        accountUseCaseService.linkAccountToFamily(accountId, familyId);
        return true;
    }

    @MutationMapping
    public boolean transferMoney(@Argument UUID from,
                                 @Argument UUID to,
                                 @Argument String amount) {
        Money money = new Money(new BigDecimal(amount));
        return accountUseCaseService.transferMoney(new AccountId(from), new AccountId(to), money);
    }
}