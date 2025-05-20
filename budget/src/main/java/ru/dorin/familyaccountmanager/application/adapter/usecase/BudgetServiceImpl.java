package ru.dorin.familyaccountmanager.application.adapter.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.domain.aggregate.Budget;
import ru.dorin.familyaccountmanager.domain.aggregate.BudgetCategory;
import ru.dorin.familyaccountmanager.domain.aggregate.BudgetId;
import ru.dorin.familyaccountmanager.domain.exception.BudgetAlreadyCreatedException;
import ru.dorin.familyaccountmanager.domain.exception.CreateBudgetException;
import ru.dorin.familyaccountmanager.domain.port.BudgetQueryService;
import ru.dorin.familyaccountmanager.domain.port.BudgetUseCaseService;
import ru.dorin.familyaccountmanager.domain.publisher.DomainEventPublisher;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;
import ru.dorin.familyaccountmanager.integration.domain.family.FamilyIdQuery;
import ru.dorin.familyaccountmanager.integration.event.BudgetOverLimitEvent;
import ru.dorin.familyaccountmanager.integration.event.publisher.IntegrationEventPublisher;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BudgetServiceImpl implements BudgetUseCaseService {
    private final BudgetQueryService budgetQueryService;
    private final FamilyIdQuery familyIdQuery;
    private final DomainEventPublisher publisher;
    private final IntegrationEventPublisher integrationEventPublisher;

    @Override
    public BudgetId createBudget(UUID familyId, BudgetCategory category, YearMonth period, Money limits) {
        boolean familyExist = familyIdQuery.isFamilyExist(familyId);
        if (!familyExist) {
            throw new CreateBudgetException(familyId);
        }
        budgetQueryService.getBudgets(familyId)
                .stream()
                .filter(budget -> budget.similarCategories(category))
                .filter(budget -> period.equals(budget.getPeriod()))
                .findAny()
                .ifPresent(budget -> {
                    throw new BudgetAlreadyCreatedException(budget.getFamilyId(), budget.getCategory());
                });
        Budget budget = Budget.create(familyId, category, period, limits);
        publisher.publish(budget.pullDomainEvent());
        return budget.getId();
    }

    public void spend(UUID familyId, BudgetCategory category, Money money, Instant occurredAt) {
        boolean familyExist = familyIdQuery.isFamilyExist(familyId);
        if (!familyExist) {
            throw new CreateBudgetException(familyId);
        }
        budgetQueryService.getBudgets(familyId)
                .stream()
                .filter(budget -> budget.similarCategories(category))
                .filter(budget -> YearMonth.from(occurredAt.atZone(ZoneId.systemDefault())).equals(budget.getPeriod()))
                .findAny()
                .ifPresent(budget -> {
                    budget.spend(money);
                    publisher.publish(budget.pullDomainEvent());
                    if (budget.isOverLimit()) {
                        integrationEventPublisher.publish(
                                new BudgetOverLimitEvent(
                                        budget.getId().getId(),
                                        budget.getFamilyId(),
                                        budget.getCategory().name(),
                                        Instant.now(),
                                        budget.getLimits().amount(),
                                        budget.getSpent().amount()));
                    }
                });
    }
}
