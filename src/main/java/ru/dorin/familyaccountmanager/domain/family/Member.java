package ru.dorin.familyaccountmanager.domain.family;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Member {
    private final MemberId id;
    private final MemberName name;
    private final Role role;

    public Member(String name, Role role) {
        this.id = new MemberId(UUID.randomUUID());
        this.name = new MemberName(name);
        this.role = role;
    }
}
