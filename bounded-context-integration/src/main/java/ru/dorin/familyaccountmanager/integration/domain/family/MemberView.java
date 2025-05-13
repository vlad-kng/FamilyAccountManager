package ru.dorin.familyaccountmanager.integration.domain.family;

import java.util.UUID;

public record MemberView(
        UUID id,
        String name,
        String role
) {
}
