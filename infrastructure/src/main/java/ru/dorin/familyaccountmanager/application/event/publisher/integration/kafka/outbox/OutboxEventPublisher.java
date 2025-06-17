package ru.dorin.familyaccountmanager.application.event.publisher.integration.kafka.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.application.persistence.mongo.OutboxEventDocument;
import ru.dorin.familyaccountmanager.application.persistence.mongo.OutboxEventDocumentRepository;
import ru.dorin.familyaccountmanager.integration.event.IntegrationEvent;
import ru.dorin.familyaccountmanager.integration.event.publisher.IntegrationEventPublisher;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Integration event publisher with implementation outbox pattern
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.integration.publisher", havingValue = "outbox")
public class OutboxEventPublisher implements IntegrationEventPublisher {

    private final OutboxEventDocumentRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public <E extends IntegrationEvent> void publish(E event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            OutboxEventDocument doc = OutboxEventDocument.builder()
                    .id(UUID.randomUUID().toString())
                    .eventType(event.getClass().getName())
                    .aggregateId(event.getAggregateId().toString())
                    .payload(json)
                    .occurredAt(Instant.now())
                    .build();

            repository.save(doc);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot serialize event", e);
        }
    }

    @Override
    public <E extends IntegrationEvent> void publish(List<E> eventList) {
        eventList.forEach(this::publish);
    }
}
