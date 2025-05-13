package ru.dorin.familyaccountmanager.domain.aggregate;

import lombok.Data;

@Data
public class AccountName {
    private String name;

    public AccountName (String name) {
        short valid = 0;
        name = name.trim();
        boolean moreThan3Words = name.split(" ").length > 3;
        if (moreThan3Words) {
            valid++;
        }
        if (name.length() > 60) {
            valid++;
        }
        if (valid > 0) {
            throw new IllegalStateException("Account name is not valid");
        }
        this.name = name;

    }
}
