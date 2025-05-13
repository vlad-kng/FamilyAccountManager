package ru.dorin.familyaccountmanager.application.port.graphql.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import ru.dorin.familyaccountmanager.application.port.ErrorResponse;
import ru.dorin.familyaccountmanager.application.port.graphql.GenericGraphQLPresenter;
import ru.dorin.familyaccountmanager.application.port.graphql.GraphQLResponse;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountId;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountType;
import ru.dorin.familyaccountmanager.application.adapter.usecase.CreateAccountUseCase;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CreateAccountUseCaseResolver {
    private final CreateAccountUseCase createAccountUseCase;

    @MutationMapping
    public GraphQLResponse<UUID, ErrorResponse> createAccount(@Argument String accountName,
                                                              @Argument AccountType accountType,
                                                              @Argument String balance,
                                                              @Argument UUID familyId) {

        var presenter = new CreateAccountPresenter();

        createAccountUseCase.execute(new CreateAccountUseCase.Input(accountName, accountType, new BigDecimal(balance), familyId), presenter);
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
