package ru.dorin.familyaccountmanager.domain.aggregate;

import lombok.EqualsAndHashCode;
import ru.dorin.familyaccountmanager.domain.valueobject.DomainId;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
public class AccountId extends DomainId<Account> {

    public AccountId() {
        super();
    }
    public AccountId(String id) {
        super(id);
    }

    public AccountId(UUID id) {
        super(id);
    }
}
