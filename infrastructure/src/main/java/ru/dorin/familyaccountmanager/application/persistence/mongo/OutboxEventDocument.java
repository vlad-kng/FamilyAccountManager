package ru.dorin.familyaccountmanager.application.persistence.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "outbox_event")
public class OutboxEventDocument {
    @Id
    private String id;

    private String eventType;
    private String aggregateId;
    private String payload;
    private Instant occurredAt;

    @Builder.Default
    private OutboxStatus status = OutboxStatus.PENDING;

    public enum OutboxStatus {
        PENDING, SENT, FAILED
    }
}
