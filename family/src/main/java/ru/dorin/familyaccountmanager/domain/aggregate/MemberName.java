package ru.dorin.familyaccountmanager.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberName {
    private final String name;
}
