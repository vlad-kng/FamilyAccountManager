package ru.dorin.familyaccountmanager.application.adapter.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.application.integration.event.AddBudgetIntegrationEvent;
import ru.dorin.familyaccountmanager.application.integration.event.LinkAccountToFamilyIntegrationEvent;
import ru.dorin.familyaccountmanager.domain.port.usecase.FamilyUseCaseService;
import ru.dorin.familyaccountmanager.application.publisher.DomainEventPublisher;
import ru.dorin.familyaccountmanager.domain.account.AccountId;
import ru.dorin.familyaccountmanager.domain.budget.BudgetId;
import ru.dorin.familyaccountmanager.domain.event.family.FamilyAccountLinkedEvent;
import ru.dorin.familyaccountmanager.domain.event.family.FamilyBudgetLinkedEvent;
import ru.dorin.familyaccountmanager.domain.event.family.FamilyCreatedEvent;
import ru.dorin.familyaccountmanager.domain.event.family.MemberAddedEvent;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;
import ru.dorin.familyaccountmanager.domain.family.Member;
import ru.dorin.familyaccountmanager.domain.family.Role;
import ru.dorin.familyaccountmanager.domain.family.Surname;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class FamilyUserCaseServiceImpl implements FamilyUseCaseService {
    private final DomainEventPublisher publisher;

    @Override
    public FamilyId createFamily(String surname) {
        FamilyId id = new FamilyId();
        Surname familySurname = new Surname(surname);
        var event = new FamilyCreatedEvent(id, familySurname, Instant.now());
        publisher.publish(event);
        return id;
    }

    @Override
    public void addMember(FamilyId familyId, String memberName, Role role) {
        Member member = new Member(memberName, role);
        var event = new MemberAddedEvent(familyId, member, Instant.now());
        publisher.publish(event);
    }

    @Override
    public void linkAccountToFamily(FamilyId familyId, AccountId accountId) {
        var event = new FamilyAccountLinkedEvent(familyId, accountId, Instant.now());
        publisher.publish(event);
    }

    @Override
    public void addBudget(FamilyId familyId, BudgetId budgetId) {
        var event = new FamilyBudgetLinkedEvent(familyId, budgetId, Instant.now());
        publisher.publish(event);
    }

    @EventListener
    void handle(LinkAccountToFamilyIntegrationEvent event) {
       linkAccountToFamily(event.familyId(), event.accountId());
    }

    @EventListener
    void handle(AddBudgetIntegrationEvent event) {
        addBudget(event.familyId(), event.budgetId());
    }
}
