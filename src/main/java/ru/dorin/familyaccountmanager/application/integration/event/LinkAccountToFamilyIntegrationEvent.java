package ru.dorin.familyaccountmanager.application.integration.event;

import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.time.Instant;


public record LinkAccountToFamilyIntegrationEvent(
        AccountId accountId,
         FamilyId familyId,
        Instant occurredAt
) implements IntegrationEvent {
}