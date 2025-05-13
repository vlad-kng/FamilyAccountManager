package ru.dorin.familyaccountmanager.integration.domain.budget;

import java.util.List;
import java.util.UUID;

public interface BudgetIdQuery {
    List<UUID> getBudgetIds(UUID familyUUID);
}
