package ru.dorin.familyaccountmanager.integration.domain.budget;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

public record BudgetView(
        UUID id,
        UUID familyId,
        String category,
        YearMonth period,
        BigDecimal limits,
        BigDecimal spent
) {
}
