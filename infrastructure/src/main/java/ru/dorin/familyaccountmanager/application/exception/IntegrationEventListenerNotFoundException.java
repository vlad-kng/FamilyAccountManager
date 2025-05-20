package ru.dorin.familyaccountmanager.application.exception;

import ru.dorin.familyaccountmanager.domain.exception.LocalizedException;

public class IntegrationEventListenerNotFoundException extends LocalizedException {
    public final static String MESSAGE = "integration.event.listener.not.found";
    public IntegrationEventListenerNotFoundException() {
        super(MESSAGE);
    }
}
