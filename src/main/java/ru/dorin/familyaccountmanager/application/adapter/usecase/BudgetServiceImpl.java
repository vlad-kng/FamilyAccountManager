package ru.dorin.familyaccountmanager.application.adapter.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.integration.event.WithdrawBalanceBudgetEvent;
import ru.dorin.familyaccountmanager.budget.BudgetCategory;
import ru.dorin.familyaccountmanager.budget.BudgetId;
import ru.dorin.familyaccountmanager.event.budget.BudgetCreatedEvent;
import ru.dorin.familyaccountmanager.event.budget.BudgetSpentEvent;
import ru.dorin.familyaccountmanager.exception.BudgetAlreadyCreatedException;
import ru.dorin.familyaccountmanager.port.query.BudgetQueryService;
import ru.dorin.familyaccountmanager.port.usecase.BudgetUseCaseService;
import ru.dorin.familyaccountmanager.platform.domain.publisher.DomainEventPublisher;
import ru.dorin.familyaccountmanager.platform.domain.valueobject.Money;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BudgetServiceImpl implements BudgetUseCaseService {
    private final BudgetQueryService budgetQueryService;
    private final DomainEventPublisher publisher;

    @Override
    public BudgetId createBudget(UUID familyId, BudgetCategory category, YearMonth period, Money limits) {
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

    public void spend(UUID familyId, BudgetCategory category, Money money, Instant occurredAt) {
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
        spend(event.familyId(), BudgetCategory.valueOf(event.category()), new Money(new BigDecimal(event.money())), event.occurredAt());
    }
}
