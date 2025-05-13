package ru.dorin.familyaccountmanager.application.adapter.query.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.domain.aggregate.FamilyId;
import ru.dorin.familyaccountmanager.domain.event.FamilyEvent;
import ru.dorin.familyaccountmanager.domain.port.FamilyQueryService;
import ru.dorin.familyaccountmanager.integration.domain.budget.BudgetIdQuery;
import ru.dorin.familyaccountmanager.domain.port.EventStore;
import ru.dorin.familyaccountmanager.integration.domain.family.FamilyIdQuery;

import java.util.List;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class MongoFamilyQueryService implements FamilyQueryService, BudgetIdQuery, FamilyIdQuery {
    private final EventStore<Family, FamilyEvent> eventStore;

    public Family getFamily(FamilyId familyId) {
        return Family.recreateFromEvents(eventStore.load(familyId));
    }

    @Override
    public Family getFamily(UUID familyId) {
        return getFamily(new FamilyId(familyId));
    }

    @Override
    public List<UUID> getBudgetIds(UUID familyUUID) {
        return getFamily(familyUUID).getBudgetIds();
    }

    @Override
    public boolean isFamilyExist(UUID familyId) {
        return eventStore.contains(new FamilyId(familyId));
    }
}
