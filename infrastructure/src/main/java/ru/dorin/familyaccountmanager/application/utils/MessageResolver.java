package ru.dorin.familyaccountmanager.application.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Component
@Slf4j
public class MessageResolver {
    private static final ResourceBundle messages = ResourceBundle.getBundle("messages");

    public String getResolvedMessage(String key, Object...args) {
       return getResolvedMessage(messages, key, args);
    }

    public String getResolvedMessage(ResourceBundle bundle, String key, Object...args) {
        try {
            String template = bundle.getString(key);
            return String.format(template, args);
        } catch (MissingResourceException e) {
            log.debug("Missing message for key: {}", key);
            return "";
        }
    }
}
