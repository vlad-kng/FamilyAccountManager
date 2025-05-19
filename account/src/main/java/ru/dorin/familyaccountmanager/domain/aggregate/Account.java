package ru.dorin.familyaccountmanager.domain.aggregate;

import lombok.Getter;
import lombok.Setter;
import ru.dorin.familyaccountmanager.domain.event.AccountCreatedEvent;
import ru.dorin.familyaccountmanager.domain.event.AccountEvent;
import ru.dorin.familyaccountmanager.domain.event.InitialBalanceEvent;
import ru.dorin.familyaccountmanager.domain.event.MoneyDepositedEvent;
import ru.dorin.familyaccountmanager.domain.event.MoneyTransferReceivedEvent;
import ru.dorin.familyaccountmanager.domain.event.MoneyWithdrawalEvent;
import ru.dorin.familyaccountmanager.domain.event.TransferMoneyEvent;
import ru.dorin.familyaccountmanager.domain.exception.InvalidInitialBalanceException;
import ru.dorin.familyaccountmanager.domain.exception.NotEnoughMoneyException;
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
            throw new InvalidInitialBalanceException(id, initialBalance.amount());
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

//    public List<AccountEvent> pullDomainEvent() {
//        var copy = List.copyOf(domainEvents);
//        domainEvents.clear();
//        return copy;
//    }

    public void increaseBalance(Money money) {
        balance = balance.add(money);
    }

    public void decreaseBalance(Money money) {
        if (!canWithdrawal(money)) {
            throw new NotEnoughMoneyException(this.getBalance().amount(), money.amount());
        }
        balance = balance.subtract(money);
    }

    public void depositMoney(Money money) {
        increaseBalance(money);
        domainEvents.add(new MoneyDepositedEvent(this.id, Instant.now(), money));
    }

    public void withdrawMoney(Money money) {
        withdrawMoney(money, DEFAULT_CATEGORY);
    }

    public void withdrawMoney(Money money, String category) {
        decreaseBalance(money);
        domainEvents.add(new MoneyWithdrawalEvent(this.id, category, Instant.now(), money));
    }

    public void transferMoney(Money money, AccountId to) {
        decreaseBalance(money);
        domainEvents.add(new TransferMoneyEvent(this.id, to, Instant.now(), money));
    }

    public void receiveMoney(Money money, AccountId from) {
        increaseBalance(money);
        domainEvents.add(new MoneyTransferReceivedEvent(this.id, from, Instant.now(), money));
    }

    public void linkToFamily(UUID familyId) {
        this.familyId = familyId;
    }

    public boolean canWithdrawal(Money money) {
        return balance.isGreaterThan(money);
    }

    public static final String DEFAULT_CATEGORY = "UTILITY";
}
