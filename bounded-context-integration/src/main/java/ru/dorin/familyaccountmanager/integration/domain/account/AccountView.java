package ru.dorin.familyaccountmanager.integration.domain.account;

import java.util.UUID;

public record AccountView(
        UUID id,
        String name,
        String accountType,
        UUID familyId,
        String balance
) {
}
