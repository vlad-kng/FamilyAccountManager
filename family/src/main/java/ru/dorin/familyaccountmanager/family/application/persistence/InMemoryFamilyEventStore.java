package ru.dorin.familyaccountmanager.family.application.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.dorin.familyaccountmanager.family.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.family.domain.event.FamilyEvent;
import ru.dorin.familyaccountmanager.platform.domain.port.AbstractInMemoryEventStore;


@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.eventStore.type", havingValue = "inMemory")
@Repository
public class InMemoryFamilyEventStore extends AbstractInMemoryEventStore<Family, FamilyEvent> {
}
