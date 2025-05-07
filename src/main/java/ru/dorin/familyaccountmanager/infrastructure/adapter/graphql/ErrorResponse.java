package ru.dorin.familyaccountmanager.infrastructure.adapter.graphql;

public record ErrorResponse(
        String errorCode,
        String message
) {
}