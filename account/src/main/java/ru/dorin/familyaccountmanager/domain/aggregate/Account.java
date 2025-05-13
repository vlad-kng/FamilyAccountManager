package ru.dorin.familyaccountmanager.domain.aggregate;

import lombok.Getter;
import lombok.Setter;
import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.event.AccountCreatedEvent;
import ru.dorin.familyaccountmanager.domain.event.AccountEvent;
import ru.dorin.familyaccountmanager.domain.event.InitialBalanceEvent;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Account extends AbstractDomainAggregate<Account> {
    private AccountId id;
    private AccountName name;
    private AccountType accountType;
    private UUID familyId;
    private Money balance = Money.zero();
    private final List<AccountEvent> domainEvents = new ArrayList<>();

    private Account() {}

    public static Account recreateFromEvents(List<AccountEvent> events) {
        Account account = new Account();
        account.recreateFrom(events);
        return account;
    }

    private Account(AccountId id, AccountName name, AccountType accountType, UUID familyId, Money initialBalance) {
        if (accountType.equals(AccountType.SAVING) && initialBalance.isLessThan(new Money(new BigDecimal(20000)))) {
            throw new IllegalStateException("Initial balance for saving account must be more than 20 thousand");
        }
        this.id = id;
        this.name = name;
        this.accountType = accountType;
        this.familyId = familyId;
        this.balance = initialBalance;

        domainEvents.add(new AccountCreatedEvent(id, name, accountType, familyId, Instant.now()));
        domainEvents.add(new InitialBalanceEvent(id, initialBalance, Instant.now()));
    }

    public static Account create(AccountId id, AccountName name, AccountType accountType, UUID familyId, Money initialBalance) {
        return new Account(id, name, accountType, familyId, initialBalance);
    }

    public List<AccountEvent> pullDomainEvent() {
        var copy = List.copyOf(domainEvents);
        domainEvents.clear();
        return copy;
    }

    public void increaseBalance(Money money) {
        balance = balance.add(money);
    }

    public void withdrawBalance(Money money) {
        balance = balance.subtract(money);
    }

    public void linkToFamily(UUID familyId) {
        this.familyId = familyId;
    }

    public boolean canWithdrawal(Money money) {
        return balance.isGreaterThan(money);
    }
}
