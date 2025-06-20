package ru.dorin.familyaccountmanager.application.port;

public record ErrorResponse(
        String errorCode,
        String message
) {
}