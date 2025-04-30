package ru.dorin.familyaccountmanager.infrastructure.listener.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.application.listener.ProcessingEventListener;
import ru.dorin.familyaccountmanager.application.port.FamilyService;
import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.event.account.AccountLinkedEvent;

@Component
@RequiredArgsConstructor
public class AccountLinkedEventListener implements ProcessingEventListener<Account, AccountLinkedEvent> {

    private final FamilyService familyService;

    @Override
    public Class<AccountLinkedEvent> eventType() {
        return AccountLinkedEvent.class;
    }

    @Override
    public void afterStore(AccountLinkedEvent event) {
        //TODO integrationEvent
        familyService.linkAccountToFamily(event.familyId(), event.accountId());
    }
}
