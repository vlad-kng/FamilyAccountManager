package ru.dorin.familyaccountmanager.budget.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.dorin.familyaccountmanager.platform.domain.DomainId;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class FamilyId extends DomainId<Budget> {
    private UUID id;

    public FamilyId() {
        this.id = UUID.randomUUID();
    }
    public FamilyId(String id) {
        this.id = UUID.fromString(id);
    }
}
