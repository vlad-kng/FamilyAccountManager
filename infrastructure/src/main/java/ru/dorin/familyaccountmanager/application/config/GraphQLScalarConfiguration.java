package ru.dorin.familyaccountmanager.application.config;

import graphql.GraphQLContext;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.time.YearMonth;
import java.util.Locale;

@Configuration
public class GraphQLScalarConfiguration {


    public GraphQLScalarType yearMonthScalar() {
        return GraphQLScalarType.newScalar()
                .name("YearMonthScalar")
                .description("Year and month in the format yyyy-MM")
                .coercing(new Coercing<YearMonth, String>() {

                    @Override
                    public String serialize(@NotNull Object dataFetcherResult, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingSerializeException {
                        if (dataFetcherResult instanceof YearMonth yearMonth) {
                            return yearMonth.toString(); // например, "2025-04"
                        }
                        throw new CoercingParseLiteralException("Expected a YearMonth object.");
                    }

                    @Override
                    public YearMonth parseValue(@NotNull Object input, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseValueException {
                        if (input instanceof String str) {
                            return YearMonth.parse(str);
                        }
                        throw new CoercingParseLiteralException("Expected a String.");
                    }

                    @Override
                    public @NotNull Value<?> valueToLiteral(@NotNull Object input, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) {
                        if (input instanceof YearMonth yearMonth) {
                            return StringValue.newStringValue(yearMonth.toString()).build();
                        }
                        throw new CoercingParseLiteralException("Expected a StringValue.");
                    }
                })
                .build();
    }

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return builder -> builder.scalar(yearMonthScalar());
    }
}