package ru.dorin.familyaccountmanager.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record MemberName(String name) {
    @JsonCreator
    public MemberName {
    }

    @JsonValue
    public String toJson() {
        return name;
    }
}
