package ru.dorin.familyaccountmanager.domain.exception;

import ru.dorin.familyaccountmanager.domain.aggregate.DomainId;

public class EmptyEventsException extends LocalizedException {
    public static final String MESSAGE = "account.events.is.empty";
    public static final String FAMILY_MESSAGE = "family.events.is.empty";
    public EmptyEventsException() {
        super(MESSAGE);
    }

    public EmptyEventsException(DomainId domainId) {
        super(MESSAGE, domainId);
    }
}
