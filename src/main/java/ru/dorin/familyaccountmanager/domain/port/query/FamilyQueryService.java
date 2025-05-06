package ru.dorin.familyaccountmanager.domain.port.query;

import ru.dorin.familyaccountmanager.domain.family.Family;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

public interface FamilyQueryService {
    Family getFamily(FamilyId familyId);
}
