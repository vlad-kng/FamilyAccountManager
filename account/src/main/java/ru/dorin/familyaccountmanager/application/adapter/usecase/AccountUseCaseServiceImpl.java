package ru.dorin.familyaccountmanager.application.adapter.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.domain.aggregate.Account;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountId;
import ru.dorin.familyaccountmanager.domain.event.AccountEvent;
import ru.dorin.familyaccountmanager.domain.port.query.AccountQueryService;
import ru.dorin.familyaccountmanager.domain.port.usecase.AccountUseCaseService;
import ru.dorin.familyaccountmanager.domain.publisher.DomainEventPublisher;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountUseCaseServiceImpl implements AccountUseCaseService {
    private final AccountQueryService accountQueryService;
    private final DomainEventPublisher publisher;

    @Override
    public boolean increaseBalance(AccountId accountId, BigDecimal amount) {
        Account account = accountQueryService.getAccount(accountId);
        account.depositMoney(new Money(amount));
        publisher.publish(account.pullDomainEvent());
        return true;
    }

    @Override
    public boolean withdrawBalance(AccountId accountId, BigDecimal amount, String category) {
        Money money = new Money(amount);
        Account account = accountQueryService.getAccount(accountId);
        account.withdrawMoney(money, category);
        publisher.publish(account.pullDomainEvent());
        return true;
    }

    @Override
    public boolean transferMoney(AccountId from, AccountId to, Money money) {
        Account accountFrom = accountQueryService.getAccount(from);
        Account accountTo = accountQueryService.getAccount(to);
        accountFrom.transferMoney(money, to);
        accountTo.receiveMoney(money, from);
        List<AccountEvent> aggregatedEvents = new ArrayList<>();
        aggregatedEvents.addAll(accountTo.pullDomainEvent());
        aggregatedEvents.addAll(accountFrom.pullDomainEvent());
        publisher.publish(aggregatedEvents);
        return true;
    }
}
