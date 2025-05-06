package ru.dorin.familyaccountmanager.infrastructure.persistence.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import ru.dorin.familyaccountmanager.domain.port.EventStore;
import ru.dorin.familyaccountmanager.domain.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.DomainId;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;
import ru.dorin.familyaccountmanager.infrastructure.config.EventObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
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
        return event.getAggregateId() + "-" + event.occurredAt().toEpochMilli();
    }

    @Override
    public boolean contains(DomainId<Aggregate> aggregateId) {
        return mongoTemplate.exists(
                query(where("aggregateId").is(aggregateId.toString())),
                StoredEventDocument.class);
    }
}
