package ru.dorin.familyaccountmanager.infrastructure.adapter.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import ru.dorin.familyaccountmanager.application.AccountService;
import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.account.AccountType;
import ru.dorin.familyaccountmanager.domain.account.Money;
import ru.dorin.familyaccountmanager.domain.account.TransactionDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AccountResolver {
    private final AccountService accountService;

    @QueryMapping
    public Account getAccount(@Argument UUID id) {
        return accountService.getAccount(new AccountId(id));
    }

    @QueryMapping
    public List<TransactionDTO> getTransactions(@Argument UUID accountId) {
        return accountService.getTransactions(new AccountId(accountId));
    }

    @SchemaMapping(typeName = "Account", field = "transactions")
    public List<TransactionDTO> getTransactionsForAccount(Account account) {
        return accountService.getTransactions(account.getId());
    }

    @MutationMapping
    public AccountId createAccount(@Argument String accountName,
                                   @Argument AccountType accountType,
                                   @Argument String balance) {
        return accountService.createAccount(accountName, accountType, balance);
    }

    @MutationMapping
    public boolean increaseBalance(@Argument UUID id,
                                   @Argument String amount) {
        return accountService.increaseBalance(new AccountId(id), new BigDecimal(amount));
    }

    @MutationMapping
    public boolean withdrawBalance(@Argument UUID id,
                                   @Argument String amount) {
        return accountService.withdrawBalance(new AccountId(id), new BigDecimal(amount));
    }

    @MutationMapping
    public boolean transferMoney(@Argument UUID from,
                                 @Argument UUID to,
                                 @Argument String amount) {
        Money money = new Money(new BigDecimal(amount));
        return accountService.transferMoney(new AccountId(from), new AccountId(to), money);
    }
}