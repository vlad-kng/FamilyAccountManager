package ru.dorin.familyaccountmanager.domain.aggregate;

import lombok.EqualsAndHashCode;
import ru.dorin.familyaccountmanager.domain.valueobject.DomainId;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
public class BudgetId extends DomainId<Budget> {

    public BudgetId() {
       super();
    }

    public BudgetId(UUID id) {
        super(id);
    }

    public BudgetId(String id) {
        super(id);
    }
}
