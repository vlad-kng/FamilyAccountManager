package ru.dorin.familyaccountmanager.application.event.listener.mongo;

import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.application.persistence.mongo.MongoEventStore;
import ru.dorin.familyaccountmanager.domain.listener.AbstractStoringEventListener;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

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
