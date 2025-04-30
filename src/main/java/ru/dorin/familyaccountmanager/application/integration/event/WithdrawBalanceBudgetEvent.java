package ru.dorin.familyaccountmanager.application.integration.event;

import ru.dorin.familyaccountmanager.domain.account.Money;
import ru.dorin.familyaccountmanager.domain.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.time.Instant;


public record WithdrawBalanceBudgetEvent (
        FamilyId familyId,
        BudgetCategory category,
        Money money,
        Instant occurredAt
) {
}