package ru.dorin.familyaccountmanager.domain.account;

import ru.dorin.familyaccountmanager.domain.exception.InvalidMoneyValueException;

import java.math.BigDecimal;

public record Money(BigDecimal amount) {
    public Money {
        if (amount == null || amount.signum() <= 0) {
            throw new InvalidMoneyValueException(amount);
        }
    }

    public Money negate() {
        return new Money(amount.negate());
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money other) {
        return new Money(this.amount.subtract(other.amount));
    }
}