package ru.dorin.familyaccountmanager.account;

import lombok.Getter;
import lombok.Setter;
import ru.dorin.familyaccountmanager.platform.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.platform.domain.DomainId;
import ru.dorin.familyaccountmanager.platform.domain.valueobject.Money;

import java.util.UUID;

@Getter
@Setter
public class Account extends AbstractDomainAggregate<Account> {
    private AccountId id;
    private AccountName name;
    private AccountType accountType;
    private UUID familyId;
    private Money balance = Money.zero();

    public Account(AccountId id) {
        this.id = id;
    }

    public Account(DomainId<Account> accountDomainId) {
        this.id = (AccountId) accountDomainId;
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
