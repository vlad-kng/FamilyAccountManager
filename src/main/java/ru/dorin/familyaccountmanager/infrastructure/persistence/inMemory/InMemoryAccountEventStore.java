package ru.dorin.familyaccountmanager.infrastructure.persistence.inMemory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.dorin.familyaccountmanager.account.Account;
import ru.dorin.familyaccountmanager.event.account.AccountEvent;
import ru.dorin.familyaccountmanager.platform.domain.port.AbstractInMemoryEventStore;

@Repository
@RequiredArgsConstructor
public class InMemoryAccountEventStore extends AbstractInMemoryEventStore<Account, AccountEvent> {
}
