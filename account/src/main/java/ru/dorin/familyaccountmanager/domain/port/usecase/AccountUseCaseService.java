package ru.dorin.familyaccountmanager.domain.port.usecase;

import ru.dorin.familyaccountmanager.domain.aggregate.AccountId;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountUseCaseService {
    boolean increaseBalance(AccountId accountId, BigDecimal amount);
    boolean withdrawBalance(AccountId accountId, BigDecimal amount, String category);
    void linkAccountToFamily(UUID accountId, UUID familyId);
    boolean transferMoney(AccountId from, AccountId to, Money money);
}
