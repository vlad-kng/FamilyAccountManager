package ru.dorin.familyaccountmanager.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.dorin.familyaccountmanager.domain.DomainId;

import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
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
