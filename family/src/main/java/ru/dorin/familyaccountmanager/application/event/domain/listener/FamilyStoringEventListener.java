package ru.dorin.familyaccountmanager.application.event.domain.listener;

import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.domain.event.FamilyEvent;
import ru.dorin.familyaccountmanager.domain.listener.AbstractStoringEventListener;
import ru.dorin.familyaccountmanager.domain.port.EventStore;

@Component
public class FamilyStoringEventListener extends AbstractStoringEventListener<Family, FamilyEvent> {

    public FamilyStoringEventListener(EventStore<Family, FamilyEvent> eventStore) {
        super(eventStore);
    }

    @Override
    public Class<FamilyEvent> eventType() {
        return FamilyEvent.class;
    }
}