package ru.dorin.familyaccountmanager.domain.account;

import lombok.Getter;
import lombok.Setter;
import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.DomainId;

import java.math.BigDecimal;

@Getter
@Setter
public class Account extends AbstractDomainAggregate<Account> {
    private AccountId id;
    private AccountName name;
    private AccountType accountType;
    private BigDecimal balance = BigDecimal.ZERO;

    public Account(AccountId id) {
        this.id = id;
    }

    public Account(DomainId<Account> accountDomainId) {
        this.id = (AccountId) accountDomainId;
    }

    public void increaseBalance(Money money) {
        balance = balance.add(money.amount());
    }

    public void withdrawBalance(Money money) {
        balance = balance.subtract(money.amount());
    }

}
