package ru.dorin.familyaccountmanager.infrastructure.persistence.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import ru.dorin.familyaccountmanager.platform.domain.port.EventStore;
import ru.dorin.familyaccountmanager.platform.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.platform.domain.DomainId;
import ru.dorin.familyaccountmanager.platform.domain.event.DomainEvent;
import ru.dorin.familyaccountmanager.infrastructure.config.EventObjectMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

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
                query(where("aggregateId").is(aggregateId.toString())),
                StoredEventDocument.class
        );

        return docs.stream()
                .map(doc -> {
                    try {
                        return (Event) eventObjectMapper.getMapper().readValue(doc.getEventJson(), DomainEvent.class);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to deserialize event", e);
                    }
                })
                .collect(Collectors.toList());
    }

    private String generateId(Event event) {
        return event.getAggregateId() + "-" + UUID.randomUUID();
    }

    @Override
    public boolean contains(DomainId<Aggregate> aggregateId) {
        return mongoTemplate.exists(
                query(where("aggregateId").is(aggregateId.toString())),
                StoredEventDocument.class);
    }
}
