package ru.dorin.familyaccountmanager.family.domain.port;


import ru.dorin.familyaccountmanager.family.domain.aggregate.Family;

import java.util.UUID;

public interface FamilyQueryService {
    Family getFamily(UUID familyId);
}
