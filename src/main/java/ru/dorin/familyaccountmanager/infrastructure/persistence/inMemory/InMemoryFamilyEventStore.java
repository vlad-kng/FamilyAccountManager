package ru.dorin.familyaccountmanager.infrastructure.persistence.inMemory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.dorin.familyaccountmanager.domain.event.family.FamilyEvent;
import ru.dorin.familyaccountmanager.domain.family.Family;

@Repository
@RequiredArgsConstructor
public class InMemoryFamilyEventStore extends AbstractInMemoryEventStore<Family, FamilyEvent> {
}
