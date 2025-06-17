package ru.dorin.familyaccountmanager.application.event.publisher.integration.kafka.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.application.event.publisher.integration.kafka.KafkaIntegrationEventSender;
import ru.dorin.familyaccountmanager.application.persistence.mongo.OutboxEventDocument;
import ru.dorin.familyaccountmanager.application.persistence.mongo.OutboxEventDocumentRepository;
import ru.dorin.familyaccountmanager.integration.event.IntegrationEvent;

import java.util.List;

@Slf4j
@Component
@ConditionalOnProperty(value = "application.integration.publisher", havingValue = "outbox")
@RequiredArgsConstructor
public class OutboxEventProcessor {

    private final OutboxEventDocumentRepository repository;
    private final KafkaIntegrationEventSender kafkaSender;
    private final ObjectMapper objectMapper;



    @Scheduled(fixedDelayString = "${application.kafka.outbox.relay-interval-ms}")
    public void relay() {
        List<OutboxEventDocument> events = repository.findTop100ByStatusOrderByOccurredAtAsc(OutboxEventDocument.OutboxStatus.PENDING);
        for (OutboxEventDocument event : events) {
            try {
                IntegrationEvent integrationEvent = (IntegrationEvent) objectMapper.readValue(event.getPayload(), Class.forName(event.getEventType()));
                kafkaSender.send(integrationEvent).get();
                event.setStatus(OutboxEventDocument.OutboxStatus.SENT);
                repository.save(event);
                log.info("Sent and deleted outbox event: {}", event.getId());

            } catch (Exception e) {
                log.error("Failed to send outbox event: {}", event.getId(), e);
                event.setStatus(OutboxEventDocument.OutboxStatus.FAILED);
                repository.save(event);
            }
        }
    }
}
