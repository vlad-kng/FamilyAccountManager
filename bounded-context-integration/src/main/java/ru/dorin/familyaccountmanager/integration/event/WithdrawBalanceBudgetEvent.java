package ru.dorin.familyaccountmanager.integration.event;

import java.time.Instant;
import java.util.UUID;


public record WithdrawBalanceBudgetEvent (
        UUID familyId,
        String category,
        String money,
        Instant occurredAt
) implements IntegrationEvent {
}