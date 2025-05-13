package ru.dorin.familyaccountmanager.application.port.graphql;

import lombok.Getter;
import lombok.Setter;
import ru.dorin.familyaccountmanager.application.port.ErrorResponse;

@Getter
@Setter
public class GenericGraphQLPresenter <T> {
    private GraphQLResponse<T, ErrorResponse> response;
}
