package ru.dorin.familyaccountmanager.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.application.port.EventStore;
import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.event.family.AccountLinkedEvent;
import ru.dorin.familyaccountmanager.domain.event.family.FamilyCreatedEvent;
import ru.dorin.familyaccountmanager.domain.event.family.FamilyEvent;
import ru.dorin.familyaccountmanager.domain.event.family.MemberAddedEvent;
import ru.dorin.familyaccountmanager.domain.family.Family;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;
import ru.dorin.familyaccountmanager.domain.family.Member;
import ru.dorin.familyaccountmanager.domain.family.Role;
import ru.dorin.familyaccountmanager.domain.family.Surname;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {
    private final EventStore<Family, FamilyEvent> eventStore;

    @Override
    public FamilyId createFamily(String surname) {
        FamilyId id = new FamilyId();
        Surname familySurname = new Surname(surname);
        var event = new FamilyCreatedEvent(id, familySurname, Instant.now());
        eventStore.append(id, event);
        return id;
    }

    @Override
    public void addMember(FamilyId familyId, String memberName, Role role) {
        Member member = new Member(memberName, role);
        var event = new MemberAddedEvent(familyId, member, Instant.now());
        eventStore.append(familyId, event);
    }

    @Override
    public void linkAccountToFamily(FamilyId familyId, AccountId accountId) {
        var event = new AccountLinkedEvent(familyId, accountId, Instant.now());
        eventStore.append(familyId, event);
    }

    public Family getFamily(FamilyId familyId) {
        var events = eventStore.load(familyId);
        Family family = new Family(familyId);
        return family.recreateFrom(events);
    }
}
