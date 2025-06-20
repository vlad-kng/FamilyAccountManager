package ru.dorin.familyaccountmanager.domain.aggregate;

import lombok.EqualsAndHashCode;
import ru.dorin.familyaccountmanager.domain.valueobject.DomainId;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
public class FamilyId extends DomainId<Family> {

    public FamilyId() {
        super();
    }
    public FamilyId(String id) {
        super(id);
    }
    public FamilyId(UUID id) {
        super(id);
    }
}
