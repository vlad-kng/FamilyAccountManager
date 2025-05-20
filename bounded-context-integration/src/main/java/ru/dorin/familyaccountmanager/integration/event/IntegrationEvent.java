package ru.dorin.familyaccountmanager.integration.event;

import java.util.UUID;

public interface IntegrationEvent {
    UUID getAggregateId();
}
