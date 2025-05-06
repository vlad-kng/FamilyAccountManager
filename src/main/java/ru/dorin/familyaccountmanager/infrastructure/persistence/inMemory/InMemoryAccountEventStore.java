package ru.dorin.familyaccountmanager.infrastructure.persistence.inMemory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.event.account.AccountEvent;

@Repository
@RequiredArgsConstructor
public class InMemoryAccountEventStore extends AbstractInMemoryEventStore<Account, AccountEvent> {
}
