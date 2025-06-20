package ru.dorin.familyaccountmanager.domain.exception;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class LocalizedException extends RuntimeException {
    private static final ResourceBundle messages = ResourceBundle.getBundle("exceptionMessages");

    public LocalizedException(String messageKey) {
        super(resolveMessage(messageKey));
    }

    private static String resolveMessage(String key) {
        try {
            return messages.getString(key);
        } catch (MissingResourceException e) {
            return "Missing message for key: " + key;
        }
    }

    public LocalizedException(String messageKey, Object... args) {
        super(resolveMessage(messageKey, args));
    }
    public LocalizedException(String messageKey, ResourceBundle bundle, Object... args) {
        super(resolveMessage(messageKey, bundle,  args));
    }

    private static String resolveMessage(String key, Object... args) {
        try {
            String template = messages.getString(key);
            return String.format(template, args);
        } catch (MissingResourceException e) {
            return "Missing message for key: " + key;
        }
    }

    private static String resolveMessage(String key, ResourceBundle bundle, Object... args) {
        try {
            String template = bundle.getString(key);
            return String.format(template, args);
        } catch (MissingResourceException e) {
            return "Missing message for key: " + key;
        }
    }
}
