package ru.dorin.familyaccountmanager.application.adapter.mapper;

import lombok.experimental.UtilityClass;
import ru.dorin.familyaccountmanager.domain.aggregate.Account;
import ru.dorin.familyaccountmanager.integration.domain.account.AccountView;

@UtilityClass
public class AccountToViewMapper {

    public static AccountView map(Account account) {
        return new AccountView(
                account.getId().getId(),
                account.getName().getName(),
                account.getAccountType().name(),
                account.getFamilyId(),
                account.getBalance().amount().toString()
        );
    }
}
