package ru.dorin.familyaccountmanager.family.application.adapter.query.inmemory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.family.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.family.domain.aggregate.FamilyId;
import ru.dorin.familyaccountmanager.family.domain.event.FamilyEvent;
import ru.dorin.familyaccountmanager.platform.domain.port.EventStore;
import ru.dorin.familyaccountmanager.family.domain.port.FamilyQueryService;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class InMemoryFamilyQueryService implements FamilyQueryService {
    private final EventStore<Family, FamilyEvent> eventStore;

    public Family getFamily(UUID familyId) {
        FamilyId id = new FamilyId(familyId);
        var events = eventStore.load(id);
        Family family = new Family(id);
        family.recreateFrom(events);
        return family;
    }
}
