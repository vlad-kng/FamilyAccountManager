package ru.dorin.familyaccountmanager.application.adapter.usecase;

import lombok.extern.slf4j.Slf4j;
import ru.dorin.familyaccountmanager.integration.event.AddBudgetIntegrationEvent;
import ru.dorin.familyaccountmanager.integration.event.BudgetOverLimitEvent;
import ru.dorin.familyaccountmanager.integration.event.LinkAccountToFamilyIntegrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.domain.aggregate.FamilyId;
import ru.dorin.familyaccountmanager.domain.aggregate.Member;
import ru.dorin.familyaccountmanager.domain.aggregate.Role;
import ru.dorin.familyaccountmanager.domain.aggregate.Surname;
import ru.dorin.familyaccountmanager.domain.event.FamilyAccountLinkedEvent;
import ru.dorin.familyaccountmanager.domain.event.FamilyBudgetLinkedEvent;
import ru.dorin.familyaccountmanager.domain.event.FamilyCreatedEvent;
import ru.dorin.familyaccountmanager.domain.event.MemberAddedEvent;
import ru.dorin.familyaccountmanager.domain.port.FamilyUseCaseService;
import ru.dorin.familyaccountmanager.domain.publisher.DomainEventPublisher;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FamilyUseCaseServiceImpl implements FamilyUseCaseService {
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
    public void linkAccountToFamily(FamilyId familyId, UUID accountId) {
        var event = new FamilyAccountLinkedEvent(familyId, accountId, Instant.now());
        publisher.publish(event);
    }

    @Override
    public void addBudget(FamilyId familyId, UUID budgetId) {
        var event = new FamilyBudgetLinkedEvent(familyId, budgetId, Instant.now());
        publisher.publish(event);
    }

    @EventListener
    void handle(LinkAccountToFamilyIntegrationEvent event) {
       linkAccountToFamily(new FamilyId(event.familyId()), event.accountId());
    }

    @EventListener
    void handle(BudgetOverLimitEvent event) {
        //TODO notify family parents
        log.info("Family budget for category: {} exceeded. limit: {}, spent: {}", event.budgetCategory(), event.limit(), event.spent());
    }

    @EventListener
    void handle(AddBudgetIntegrationEvent event) {
        addBudget(new FamilyId(event.familyId()), event.budgetId());
    }
}
