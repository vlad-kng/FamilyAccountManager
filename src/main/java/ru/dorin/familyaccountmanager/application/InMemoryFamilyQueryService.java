package ru.dorin.familyaccountmanager.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.application.port.EventStore;
import ru.dorin.familyaccountmanager.application.port.FamilyQueryService;
import ru.dorin.familyaccountmanager.domain.event.family.FamilyEvent;
import ru.dorin.familyaccountmanager.domain.family.Family;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

@Service
@RequiredArgsConstructor
public class InMemoryFamilyQueryService implements FamilyQueryService {
    private final EventStore<Family, FamilyEvent> eventStore;

    public Family getFamily(FamilyId familyId) {
        var events = eventStore.load(familyId);
        Family family = new Family(familyId);
        family.recreateFrom(events);
        return family;
    }
}
