package ru.dorin.familyaccountmanager.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.application.port.BudgetUseCaseService;
import ru.dorin.familyaccountmanager.application.publisher.DomainEventPublisher;
import ru.dorin.familyaccountmanager.domain.account.Money;
import ru.dorin.familyaccountmanager.domain.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.budget.BudgetId;
import ru.dorin.familyaccountmanager.domain.event.budget.BudgetCreatedEvent;
import ru.dorin.familyaccountmanager.domain.event.budget.BudgetSpentEvent;
import ru.dorin.familyaccountmanager.application.integration.event.WithdrawBalanceBudgetEvent;
import ru.dorin.familyaccountmanager.domain.exception.BudgetAlreadyCreatedException;
import ru.dorin.familyaccountmanager.domain.family.FamilyId;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Slf4j
public class BudgetServiceImpl implements BudgetUseCaseService {
    private final BudgetQueryService budgetQueryService;
    private final DomainEventPublisher publisher;

    @Override
    public BudgetId createBudget(FamilyId familyId, BudgetCategory category, YearMonth period, Money limits) {
        budgetQueryService.getBudgets(familyId)
                .stream()
                .filter(budget -> budget.similarCategories(category))
                .filter(budget -> period.equals(budget.getPeriod()))
                .findAny()
                .ifPresent(budget -> {
                    throw new BudgetAlreadyCreatedException(budget.getFamilyId(), budget.getCategory());
                });
        BudgetId id = new BudgetId();
        var createdEvent = new BudgetCreatedEvent(id, familyId, category, period, limits, Instant.now());
        publisher.publish(createdEvent);
        return id;
    }

    public void spend(FamilyId familyId, BudgetCategory category, Money money, Instant occurredAt) {
        budgetQueryService.getBudgets(familyId)
                .stream()
                .filter(budget -> budget.similarCategories(category))
                .filter(budget -> YearMonth.from(occurredAt.atZone(ZoneId.systemDefault())).equals(budget.getPeriod()))
                .findAny()
                .ifPresent(budget -> {
                    var id = budget.getId();
                    var event = new BudgetSpentEvent(id, category, money, Instant.now());
                    publisher.publish(event);
                    if(budget.isOverLimit(money)) {
                        //TODO Send notification. Not throw
                        log.info("budget: {} is over limit for category: {}", budget.getId(), category);
//                        throw new OverBudgetLimitException(budget.getId(), category);
                    }
                });
    }

    @EventListener
    public void handle(WithdrawBalanceBudgetEvent event) {
        spend(event.familyId(), event.category(), event.money(), event.occurredAt());
    }
}
