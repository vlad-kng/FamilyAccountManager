package ru.dorin.familyaccountmanager.infrastructure.adapter.graphql;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericGraphQLPresenter <T> {
    private GraphQLResponse<T, ErrorResponse> response;
}
