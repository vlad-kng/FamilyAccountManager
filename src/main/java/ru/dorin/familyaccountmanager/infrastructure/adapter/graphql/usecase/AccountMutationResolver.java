package ru.dorin.familyaccountmanager.infrastructure.adapter.graphql.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import ru.dorin.familyaccountmanager.infrastructure.adapter.graphql.GenericGraphQLPresenter;
import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.account.AccountType;
import ru.dorin.familyaccountmanager.domain.port.usecase.CreateAccountUseCase;
import ru.dorin.familyaccountmanager.infrastructure.adapter.graphql.ErrorResponse;
import ru.dorin.familyaccountmanager.infrastructure.adapter.graphql.GraphQLResponse;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AccountMutationResolver {
    private final CreateAccountUseCase createAccountUseCase;

    @MutationMapping
    public GraphQLResponse<UUID, ErrorResponse> createAccount(@Argument String accountName,
                                                                              @Argument AccountType accountType,
                                                                              @Argument String balance) {

        var presenter = new CreateAccountPresenter();

        createAccountUseCase.execute(new CreateAccountUseCase.Input(accountName, accountType, new BigDecimal(balance)), presenter);
        return presenter.getResponse();
    }

    public static class CreateAccountPresenter extends GenericGraphQLPresenter<UUID> implements CreateAccountUseCase.Presenter {

        @Override
        public void presentSuccess(AccountId accountId) {
            setResponse(GraphQLResponse.success(accountId.getId()));
        }

        @Override
        public void presentFailure(String message) {
            setResponse(GraphQLResponse.error(new ErrorResponse(
             ErrorCode.ERROR.name(), message
            )));
        }

    }
    enum ErrorCode {
        ERROR
    }
}
