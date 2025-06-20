package ru.dorin.familyaccountmanager.domain.exception;

import ru.dorin.familyaccountmanager.domain.valueobject.DomainId;

public class EmptyEventsException extends LocalizedException {
    public static final String MESSAGE = "aggregate.events.is.empty";
    public EmptyEventsException() {
        super(MESSAGE);
    }

    public EmptyEventsException(DomainId domainId) {
        super(MESSAGE, domainId);
    }
}
