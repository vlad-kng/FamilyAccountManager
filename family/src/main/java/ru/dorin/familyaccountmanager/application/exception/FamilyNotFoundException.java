package ru.dorin.familyaccountmanager.application.exception;

public class FamilyNotFoundException extends RuntimeException {
    private final static String MESSAGE = "family.not.found";
    public FamilyNotFoundException() {
        super(MESSAGE);
    }
}
