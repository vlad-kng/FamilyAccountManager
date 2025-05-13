package ru.dorin.familyaccountmanager.integration.domain.family;

import java.util.UUID;

public interface FamilyIdQuery {
    boolean isFamilyExist(UUID familyId);
}
