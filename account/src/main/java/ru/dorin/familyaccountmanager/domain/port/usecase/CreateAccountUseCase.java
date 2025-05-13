package ru.dorin.familyaccountmanager.domain.port.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountId;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountName;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountType;
import ru.dorin.familyaccountmanager.domain.event.AccountCreatedEvent;
import ru.dorin.familyaccountmanager.domain.event.InitialBalanceEvent;
import ru.dorin.familyaccountmanager.domain.publisher.DomainEventPublisher;
import ru.dorin.familyaccountmanager.domain.usecase.UseCaseWithPresenter;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateAccountUseCase implements UseCaseWithPresenter<CreateAccountUseCase.Input, CreateAccountUseCase.Presenter> {

    private final DomainEventPublisher publisher;

    @Override
    public void execute(Input input, Presenter presenter) {
        try {
            AccountId id = new AccountId();
            AccountName accountName = new AccountName(input.name());
            Money money = new Money(input.balance());
            //TODO add validation to aggregate create
            var createdEvent = new AccountCreatedEvent(id, accountName, input.accountType(), Instant.now());
            var initialBalanceIncreaseEvent = new InitialBalanceEvent(id, money, Instant.now());
            publisher.publish(List.of(createdEvent, initialBalanceIncreaseEvent));
            presenter.presentSuccess(id);
        } catch (Exception e) {
            presenter.presentFailure("Create account failure: " + e.getMessage());
        }
    }

    public record Input(String name, AccountType accountType, BigDecimal balance) {}

    public interface Presenter {
        void presentSuccess(AccountId accountId);
        void presentFailure(String message);
    }
}
