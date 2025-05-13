package ru.dorin.familyaccountmanager.domain.valueobject;

import ru.dorin.familyaccountmanager.domain.exception.InvalidMoneyValueException;
import ru.dorin.familyaccountmanager.domain.exception.NotEnoughMoneyException;

import java.math.BigDecimal;

public record Money(BigDecimal amount) {
    public Money {
        if (amount == null || amount.signum() < 0) {
            throw new InvalidMoneyValueException(amount);
        }
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money withdrawal) {
        if (!isGreaterThan(withdrawal)) {
            throw new NotEnoughMoneyException(amount, withdrawal.amount);
        }
        return new Money(this.amount.subtract(withdrawal.amount));
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    public boolean isGreaterThan(Money other) {
        return this.amount.compareTo(other.amount) >= 0;
    }
}