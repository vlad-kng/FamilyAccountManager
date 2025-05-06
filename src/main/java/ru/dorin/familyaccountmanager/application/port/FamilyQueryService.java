package ru.dorin.familyaccountmanager.application.port;

import ru.dorin.familyaccountmanager.domain.family.Family;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

public interface FamilyQueryService {
    Family getFamily(FamilyId familyId);
}
