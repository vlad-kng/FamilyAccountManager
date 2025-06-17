package ru.dorin.familyaccountmanager.application.persistence.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OutboxEventDocumentRepository extends MongoRepository<OutboxEventDocument, String> {

    List<OutboxEventDocument> findTop100ByStatusOrderByOccurredAtAsc(OutboxEventDocument.OutboxStatus status);
}
