package ru.dorin.familyaccountmanager.application.port.graphql.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountId;
import ru.dorin.familyaccountmanager.domain.port.usecase.AccountUseCaseService;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AccountUseCaseResolver {

    private final AccountUseCaseService accountUseCaseService;

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
    public boolean transferMoney(@Argument UUID from,
                                 @Argument UUID to,
                                 @Argument String amount) {
        Money money = new Money(new BigDecimal(amount));
        return accountUseCaseService.transferMoney(new AccountId(from), new AccountId(to), money);
    }
}
