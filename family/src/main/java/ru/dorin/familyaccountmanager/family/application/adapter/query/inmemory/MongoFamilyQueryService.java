package ru.dorin.familyaccountmanager.family.application.adapter.query.inmemory;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.family.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.family.domain.aggregate.FamilyId;
import ru.dorin.familyaccountmanager.family.domain.port.FamilyQueryService;
import ru.dorin.familyaccountmanager.family.domain.event.FamilyEvent;
import ru.dorin.familyaccountmanager.platform.domain.port.EventStore;

import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class MongoFamilyQueryService implements FamilyQueryService {
    private final EventStore<Family, FamilyEvent> eventStore;

    public Family getFamily(FamilyId familyId) {
        var events = eventStore.load(familyId);
        Family family = new Family(familyId);
        family.recreateFrom(events);
        return family;
    }

    @Override
    public Family getFamily(UUID familyId) {
        return getFamily(new FamilyId(familyId));
    }
}
