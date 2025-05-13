package ru.dorin.familyaccountmanager.application.persistence.inmemory;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.dorin.familyaccountmanager.domain.aggregate.Account;
import ru.dorin.familyaccountmanager.domain.event.AccountEvent;
import ru.dorin.familyaccountmanager.domain.port.AbstractInMemoryEventStore;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.eventStore.type", havingValue = "inMemory")
public class InMemoryAccountEventStore extends AbstractInMemoryEventStore<Account, AccountEvent> {
}
