package ru.dorin.familyaccountmanager.integration.event;

import java.util.UUID;


public record AddBudgetIntegrationEvent(
        UUID budgetId,
        UUID familyId
) implements IntegrationEvent {
    @Override
    public UUID getAggregateId() {
        return budgetId;
    }
}