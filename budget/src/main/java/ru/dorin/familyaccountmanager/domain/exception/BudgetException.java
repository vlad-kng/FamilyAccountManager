package ru.dorin.familyaccountmanager.domain.exception;

import java.util.ResourceBundle;

public class BudgetException extends LocalizedException {
    private static final ResourceBundle messages = ResourceBundle.getBundle("budgetExceptions");

    public BudgetException(String messageKey, Object... args) {
        super(messageKey, messages, args);
    }
}
