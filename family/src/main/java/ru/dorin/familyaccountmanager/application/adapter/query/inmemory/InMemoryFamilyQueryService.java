package ru.dorin.familyaccountmanager.application.adapter.query.inmemory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.domain.aggregate.FamilyId;
import ru.dorin.familyaccountmanager.domain.event.FamilyEvent;
import ru.dorin.familyaccountmanager.domain.port.EventStore;
import ru.dorin.familyaccountmanager.domain.port.FamilyQueryService;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class InMemoryFamilyQueryService implements FamilyQueryService {
    private final EventStore<Family, FamilyEvent> eventStore;

    public Family getFamily(UUID familyId) {
        return Family.recreateFromEvents(eventStore.load(new FamilyId(familyId)));
    }
}
