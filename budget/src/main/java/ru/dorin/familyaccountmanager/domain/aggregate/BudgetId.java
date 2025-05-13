package ru.dorin.familyaccountmanager.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.dorin.familyaccountmanager.domain.DomainId;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class BudgetId extends DomainId<Budget> {
    private UUID id;

    public BudgetId() {
        this.id = UUID.randomUUID();
    }
    public BudgetId(String id) {
        this.id = UUID.fromString(id);
    }
}
