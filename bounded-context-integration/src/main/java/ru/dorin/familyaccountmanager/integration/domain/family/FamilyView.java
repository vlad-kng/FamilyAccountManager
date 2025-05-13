package ru.dorin.familyaccountmanager.integration.domain.family;

import java.util.List;
import java.util.UUID;

public record FamilyView (
        UUID id,
        String surname,
        List<MemberView>members,
        List<UUID> accountIds,
        List<UUID> budgetIds
){
}
