package ru.dorin.familyaccountmanager.family.application.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.dorin.familyaccountmanager.family.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.family.domain.event.FamilyEvent;
import ru.dorin.familyaccountmanager.platform.domain.port.AbstractInMemoryEventStore;


@RequiredArgsConstructor
public class InMemoryFamilyEventStore extends AbstractInMemoryEventStore<Family, FamilyEvent> {
}
