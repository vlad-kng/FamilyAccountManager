package ru.dorin.familyaccountmanager.infrastructure.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.dorin.familyaccountmanager.account.AccountType;

public record CreateAccountDto(
        @Schema(description = "Название счета")
        String accountName,
        @Schema(description = "Тип счета")
        AccountType accountType,
        @Schema(description = "Баланс")
        String balance
) {
}
