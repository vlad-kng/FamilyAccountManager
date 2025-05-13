package ru.dorin.familyaccountmanager.application.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.dorin.familyaccountmanager.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.domain.event.FamilyEvent;
import ru.dorin.familyaccountmanager.domain.port.AbstractInMemoryEventStore;


@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.eventStore.type", havingValue = "inMemory")
@Repository
public class InMemoryFamilyEventStore extends AbstractInMemoryEventStore<Family, FamilyEvent> {
}
