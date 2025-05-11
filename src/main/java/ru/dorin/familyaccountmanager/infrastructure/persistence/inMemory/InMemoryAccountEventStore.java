package ru.dorin.familyaccountmanager.infrastructure.persistence.inMemory;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.dorin.familyaccountmanager.account.Account;
import ru.dorin.familyaccountmanager.event.account.AccountEvent;
import ru.dorin.familyaccountmanager.platform.domain.port.AbstractInMemoryEventStore;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.eventStore.type", havingValue = "inMemory")
public class InMemoryAccountEventStore extends AbstractInMemoryEventStore<Account, AccountEvent> {
}
