package ru.dorin.familyaccountmanager.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.application.port.FamilyQueryService;
import ru.dorin.familyaccountmanager.domain.event.family.FamilyEvent;
import ru.dorin.familyaccountmanager.domain.family.Family;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;
import ru.dorin.familyaccountmanager.infrastructure.persistence.mongo.MongoEventStore;

@Service
@Primary
@RequiredArgsConstructor
public class MongoFamilyQueryService implements FamilyQueryService {
    private final MongoEventStore<Family, FamilyEvent> eventStore;

    public Family getFamily(FamilyId familyId) {
        var events = eventStore.load(familyId);
        Family family = new Family(familyId);
        family.recreateFrom(events);
        return family;
    }
}
