package ru.dorin.familyaccountmanager.application.port.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dorin.familyaccountmanager.application.adapter.dto.CreateAccountDto;
import ru.dorin.familyaccountmanager.application.port.ErrorResponse;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountId;
import ru.dorin.familyaccountmanager.application.adapter.usecase.CreateAccountUseCase;

import java.math.BigDecimal;
import java.util.UUID;

@Tag(name = "AccountApi")
@RestController
@RequiredArgsConstructor
public class CreateAccountEndpoint {
    private final CreateAccountUseCase createAccountUseCase;

    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UUID.class)))
    })
    @PostMapping("/account")
    public HttpResponse<UUID, ErrorResponse> createAccount(@RequestBody CreateAccountDto dto) {
        var presenter = new CreateAccountPresenter();
        var input = new CreateAccountUseCase.Input(dto.accountName(), dto.accountType(), new BigDecimal(dto.balance()), dto.familyId());
        createAccountUseCase.execute(input, presenter);
        return presenter.getResponse();
    }

    public static class CreateAccountPresenter extends GenericRestPresenter<UUID> implements CreateAccountUseCase.CreateAccountPresenter {

        @Override
        public void presentSuccess(AccountId accountId) {
            setResponse(HttpResponse.success(accountId.getId()));
        }

        @Override
        public void presentFailure(String message) {
            setResponse(HttpResponse.error(new ErrorResponse(
                    CreateAccountEndpoint.ErrorCode.ERROR.name(), message
            )));
        }
    }
    enum ErrorCode {
        ERROR
    }
}
