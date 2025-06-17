package ru.dorin.familyaccountmanager.application.event.listener.mongo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.application.persistence.mongo.MongoEventStore;
import ru.dorin.familyaccountmanager.domain.listener.AbstractStoringEventListener;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

@Component
@ConditionalOnProperty(value = "application.eventStore.type", havingValue = "mongo")
public class GenericDomainEventMongoListener extends AbstractStoringEventListener {

    public GenericDomainEventMongoListener(MongoEventStore<?, ?> eventStore) {
        super(eventStore);
    }

    @Override
    public Class eventType() {
        return DomainEvent.class;
    }
}
