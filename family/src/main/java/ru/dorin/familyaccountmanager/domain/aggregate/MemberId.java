package ru.dorin.familyaccountmanager.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class MemberId {
    UUID id;
}
