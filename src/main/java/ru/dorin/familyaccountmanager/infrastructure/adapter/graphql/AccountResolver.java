package ru.dorin.familyaccountmanager.infrastructure.adapter.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import ru.dorin.familyaccountmanager.domain.family.Family;
import ru.dorin.familyaccountmanager.domain.port.query.AccountQueryService;
import ru.dorin.familyaccountmanager.domain.port.query.FamilyQueryService;
import ru.dorin.familyaccountmanager.domain.port.usecase.AccountUseCaseService;
import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.account.AccountType;
import ru.dorin.familyaccountmanager.domain.account.Money;
import ru.dorin.familyaccountmanager.domain.account.TransactionDTO;
import ru.dorin.familyaccountmanager.domain.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AccountResolver {
    private final AccountUseCaseService accountUseCaseService;
    private final AccountQueryService accountQueryService;
    private final FamilyQueryService familyQueryService;

    @QueryMapping
    public Account getAccount(@Argument UUID id) {
        return accountQueryService.getAccount(new AccountId(id));
    }

    @QueryMapping
    public List<TransactionDTO> getTransactions(@Argument UUID accountId) {
        return accountQueryService.getTransactions(new AccountId(accountId));
    }

    @SchemaMapping(typeName = "Account", field = "transactions")
    public List<TransactionDTO> getTransactionsForAccount(Account account) {
        return accountQueryService.getTransactions(account.getId());
    }

    @SchemaMapping(typeName = "Account", field = "family")
    public Family getFamilyForAccount(Account account) {
        return familyQueryService.getFamily(account.getFamilyId());
    }

    @MutationMapping
    public AccountId createAccount(@Argument String accountName,
                                   @Argument AccountType accountType,
                                   @Argument String balance) {
        return accountUseCaseService.createAccount(accountName, accountType, balance);
    }

    @MutationMapping
    public boolean increaseBalance(@Argument UUID id,
                                   @Argument String amount) {
        return accountUseCaseService.increaseBalance(new AccountId(id), new BigDecimal(amount));
    }

    @MutationMapping
    public boolean withdrawBalance(@Argument UUID id,
                                   @Argument String amount,
                                   @Argument BudgetCategory category) {
        return accountUseCaseService.withdrawBalance(new AccountId(id), new BigDecimal(amount), category);
    }

    @MutationMapping
    public boolean linkAccount(@Argument UUID familyId, @Argument UUID accountId) {
        accountUseCaseService.linkAccountToFamily(new AccountId(accountId), new FamilyId(familyId));
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