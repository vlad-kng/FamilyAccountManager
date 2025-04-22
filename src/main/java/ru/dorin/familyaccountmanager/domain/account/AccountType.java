package ru.dorin.familyaccountmanager.domain.account;

import java.util.Arrays;

public enum AccountType {
    DEBIT("дебетовый"),
    SAVING("накопительный"),
    CREDIT("кредитный");

    private final String description;

    AccountType(String description) {
        this.description = description;
    }

    public static AccountType fromName(String name) {
        return Arrays.stream(values()).filter(type-> type.name().equalsIgnoreCase(name)).findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
