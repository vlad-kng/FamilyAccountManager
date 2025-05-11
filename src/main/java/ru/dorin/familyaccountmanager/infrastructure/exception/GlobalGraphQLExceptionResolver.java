package ru.dorin.familyaccountmanager.infrastructure.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.platform.domain.exception.LocalizedException;

/**
 * GraphQL exception resolver to resolve application exception to GQL format
 */
@Component
@Slf4j
public class GlobalGraphQLExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof LocalizedException localized) {
            log.warn("Localized exception occurred: {}", localized.getMessage(), localized);
            return GraphqlErrorBuilder.newError(env)
                    .message(localized.getMessage())
                    .errorType(org.springframework.graphql.execution.ErrorType.BAD_REQUEST)
                    .build();
        }
        log.error("Unexpected exception occurred: {}", ex.getMessage(), ex);
        return GraphqlErrorBuilder.newError(env)
                .message("Внутренняя ошибка сервера")
                .errorType(org.springframework.graphql.execution.ErrorType.INTERNAL_ERROR)
                .build();
    }
}
