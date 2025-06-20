package ru.dorin.familyaccountmanager.application.persistence.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ru.dorin.familyaccountmanager.application.config.EventObjectMapper;
import ru.dorin.familyaccountmanager.domain.port.EventStore;
import ru.dorin.familyaccountmanager.domain.aggregate.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.valueobject.DomainId;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.eventStore.type", havingValue = "mongo")
public class MongoEventStore<Aggregate extends AbstractDomainAggregate<Aggregate>, Event extends DomainEvent<Aggregate>>
        implements EventStore<Aggregate, Event> {

    private final MongoTemplate mongoTemplate;
    private final EventObjectMapper eventObjectMapper;

    public void append(Event event) {
        try {
            String json = eventObjectMapper.getMapper().writeValueAsString(event);

            StoredEventDocument doc = StoredEventDocument.builder()
                    .id(generateId(event))
                    .aggregateId(event.getAggregateId().toString())
                    .aggregateType(event.getAggregateClass().getSimpleName())
                    .eventType(event.getClass().getSimpleName())
                    .occurredAt(event.occurredAt())
                    .eventJson(json)
                    .build();

            mongoTemplate.save(doc);
        } catch (Exception e) {
            throw new RuntimeException("Failed to store event", e);
        }
    }

    public List<Event> load(DomainId<Aggregate> aggregateId) {
        List<StoredEventDocument> docs = mongoTemplate.find(
                Query.query(Criteria.where("aggregateId").is(aggregateId.toString())),
                StoredEventDocument.class
        );

        return (List<Event>) docs.stream()
                .map(doc -> eventObjectMapper.deserialize(doc.getEventType(), doc.getEventJson()))
                .collect(Collectors.toList());
    }

    private String generateId(Event event) {
        return event.getAggregateId() + "-" + UUID.randomUUID();
    }

    @Override
    public boolean contains(DomainId<Aggregate> aggregateId) {
        return mongoTemplate.exists(
                Query.query(Criteria.where("aggregateId").is(aggregateId.toString())),
                StoredEventDocument.class);
    }
}
