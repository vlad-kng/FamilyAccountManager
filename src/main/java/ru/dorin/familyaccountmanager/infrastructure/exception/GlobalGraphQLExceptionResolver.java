package ru.dorin.familyaccountmanager.infrastructure.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

/**
 * GraphQL exception resolver to resolve application exception to GQL format
 */
@Component
public class GlobalGraphQLExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof LocalizedException localized) {
            return GraphqlErrorBuilder.newError(env)
                    .message(localized.getMessage())
                    .errorType(org.springframework.graphql.execution.ErrorType.BAD_REQUEST)
                    .build();
        }
        return GraphqlErrorBuilder.newError(env)
                .message("Внутренняя ошибка сервера")
                .errorType(org.springframework.graphql.execution.ErrorType.INTERNAL_ERROR)
                .build();
    }
}
