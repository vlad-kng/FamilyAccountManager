package ru.dorin.familyaccountmanager.application.adapter.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dorin.familyaccountmanager.domain.aggregate.Account;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountId;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountName;
import ru.dorin.familyaccountmanager.domain.aggregate.AccountType;
import ru.dorin.familyaccountmanager.domain.publisher.DomainEventPublisher;
import ru.dorin.familyaccountmanager.domain.usecase.UseCaseWithPresenter;
import ru.dorin.familyaccountmanager.domain.valueobject.Money;
import ru.dorin.familyaccountmanager.integration.domain.family.FamilyIdQuery;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateAccountUseCase implements UseCaseWithPresenter<CreateAccountUseCase.Input, CreateAccountUseCase.Presenter> {

    private final DomainEventPublisher publisher;
    private final FamilyIdQuery familyIdQuery;

    @Override
    public void execute(Input input, Presenter presenter) {
        try {
            AccountId id = new AccountId();
            AccountName accountName = new AccountName(input.name());
            Money money = new Money(input.balance());
            boolean familyExist = familyIdQuery.isFamilyExist(input.familyId);
            if (!familyExist) {
                //TODO add domain exception
                throw new IllegalStateException("Family not found: " + input.familyId);
            }
            Account account = Account.create(id, accountName, input.accountType, input.familyId, money);
            publisher.publish(account.pullDomainEvent());
            presenter.presentSuccess(id);
        } catch (Exception e) {
            presenter.presentFailure("Create account failure: " + e.getMessage());
        }
    }

    public record Input(String name, AccountType accountType, BigDecimal balance, UUID familyId) {}

    public interface Presenter {
        void presentSuccess(AccountId accountId);
        void presentFailure(String message);
    }
}
