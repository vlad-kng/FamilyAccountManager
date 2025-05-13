package ru.dorin.familyaccountmanager.domain.aggregate;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionDTO(
        TransactionType type,
        BigDecimal amount,
        Instant occurredAt,
        String description
) {
}
