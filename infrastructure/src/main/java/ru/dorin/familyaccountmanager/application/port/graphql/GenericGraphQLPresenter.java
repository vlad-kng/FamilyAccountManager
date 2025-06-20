package ru.dorin.familyaccountmanager.application.port.graphql;

import lombok.Getter;
import lombok.Setter;
import org.springframework.graphql.GraphQlResponse;

@Getter
@Setter
public class GenericGraphQLPresenter <T> {
    private GraphQlResponse response;
}
