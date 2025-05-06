package ru.dorin.familyaccountmanager.infrastructure.listener.mongo;

import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.application.listener.AbstractStoringEventListener;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;
import ru.dorin.familyaccountmanager.infrastructure.persistence.mongo.MongoEventStore;

@Component
public class GenericDomainEventMongoListener extends AbstractStoringEventListener {

    public GenericDomainEventMongoListener(MongoEventStore<?, ?> eventStore) {
        super(eventStore);
    }

    @Override
    public Class eventType() {
        return DomainEvent.class;
    }
}
