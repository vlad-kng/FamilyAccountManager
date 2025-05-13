package ru.dorin.familyaccountmanager.application.persistence.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "events")
public class StoredEventDocument {

    @Id
    private String id;
    private String aggregateId;
    private String aggregateType;
    private String eventType;
    private Instant occurredAt;
    private String eventJson;
}
