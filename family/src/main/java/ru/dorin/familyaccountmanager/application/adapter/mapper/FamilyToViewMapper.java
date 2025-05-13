package ru.dorin.familyaccountmanager.application.adapter.mapper;

import lombok.experimental.UtilityClass;
import ru.dorin.familyaccountmanager.domain.aggregate.Family;
import ru.dorin.familyaccountmanager.domain.aggregate.Member;
import ru.dorin.familyaccountmanager.integration.domain.family.FamilyView;
import ru.dorin.familyaccountmanager.integration.domain.family.MemberView;

@UtilityClass
public class FamilyToViewMapper {

    public static FamilyView map(Family family) {
        return new FamilyView(
                family.getId().getId(),
                family.getSurname().getSurname(),
                family.getMembers().stream().map(FamilyToViewMapper::mapMember).toList(),
                family.getAccountIds(),
                family.getBudgetIds()
        );
    }

    public static MemberView mapMember(Member member) {
        return new MemberView(
                member.getId().getId(),
                member.getName().getName(),
                member.getRole().name());
    }
}
