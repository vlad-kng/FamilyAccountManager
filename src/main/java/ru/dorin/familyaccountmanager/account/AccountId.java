package ru.dorin.familyaccountmanager.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.dorin.familyaccountmanager.platform.domain.DomainId;

import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
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
