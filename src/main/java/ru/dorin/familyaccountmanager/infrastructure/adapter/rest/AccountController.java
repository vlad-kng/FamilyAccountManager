package ru.dorin.familyaccountmanager.infrastructure.adapter.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dorin.familyaccountmanager.domain.port.usecase.AccountUseCaseService;
import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.infrastructure.adapter.dto.CreateAccountDto;

import java.util.UUID;

@Tag(name = "AccountApi")
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountUseCaseService accountUseCaseService;

    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UUID.class)))
    })
    @PostMapping
    public AccountId createAccount(@RequestBody CreateAccountDto dto) {
        return accountUseCaseService.createAccount(dto.accountName(), dto.accountType(), dto.balance());
    }
}
