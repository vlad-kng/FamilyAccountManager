package ru.dorin.familyaccountmanager.domain.exception;

import ru.dorin.familyaccountmanager.domain.DomainId;
import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;
import ru.dorin.familyaccountmanager.infrastructure.exception.LocalizedException;

public class EmptyEventsException extends LocalizedException {
    public static final String MESSAGE = "account.events.is.empty";
    public static final String FAMILY_MESSAGE = "family.events.is.empty";
    public EmptyEventsException() {
        super(MESSAGE);
    }
    public EmptyEventsException(AccountId accountId) {
        super(MESSAGE, accountId);
    }
    public EmptyEventsException(FamilyId familyId) {
        super(FAMILY_MESSAGE, familyId);
    }

    public EmptyEventsException(DomainId domainId) {
        super(
            domainId instanceof FamilyId ? FAMILY_MESSAGE : MESSAGE,
            domainId
            );
    }
}
