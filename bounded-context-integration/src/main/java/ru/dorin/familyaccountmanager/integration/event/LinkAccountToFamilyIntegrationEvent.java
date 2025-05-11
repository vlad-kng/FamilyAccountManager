package ru.dorin.familyaccountmanager.integration.event;

import java.time.Instant;
import java.util.UUID;


public record LinkAccountToFamilyIntegrationEvent(
        UUID accountId,
        UUID familyId,
        Instant occurredAt
) implements IntegrationEvent {
}