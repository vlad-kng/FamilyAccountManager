package ru.dorin.familyaccountmanager.application.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountType;

import java.util.UUID;

public record CreateAccountDto(
        @Schema(description = "Название счета")
        String accountName,
        @Schema(description = "Тип счета")
        AccountType accountType,
        @Schema(description = "Баланс")
        String balance,
        @Schema(description = "Идентификатор семьи")
        UUID familyId
) {
}
