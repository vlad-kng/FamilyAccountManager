package ru.dorin.familyaccountmanager.application.integration.event;

import ru.dorin.familyaccountmanager.domain.budget.BudgetId;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;


public record AddBudgetIntegrationEvent(
        BudgetId budgetId,
         FamilyId familyId
) implements IntegrationEvent {
}