package ru.dorin.familyaccountmanager.integration.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record BudgetOverLimitEvent(
        UUID budgetId,
        UUID familyId,
        String budgetCategory,
        Instant occurredAt,
        BigDecimal limit,
        BigDecimal spent
) implements IntegrationEvent {

    @Override
    public UUID getAggregateId() {
        return budgetId;
    }
}
