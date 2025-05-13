package ru.dorin.familyaccountmanager.domain.port;


import ru.dorin.familyaccountmanager.domain.aggregate.Family;

import java.util.UUID;

public interface FamilyQueryService {
    Family getFamily(UUID familyId);
}
