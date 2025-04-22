package ru.dorin.familyaccountmanager.domain.family;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.dorin.familyaccountmanager.domain.DomainId;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FamilyId extends DomainId<Family> {
    private UUID id;

    public FamilyId() {
        this.id = UUID.randomUUID();
    }
    public FamilyId(String id) {
        this.id = UUID.fromString(id);
    }
}
