package ru.dorin.familyaccountmanager.domain.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.dorin.familyaccountmanager.domain.DomainId;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AccountId extends DomainId<Account> {
    private UUID id;

    public AccountId() {
        this.id = UUID.randomUUID();
    }
    public AccountId(String id) {
        this.id = UUID.fromString(id);
    }
}
