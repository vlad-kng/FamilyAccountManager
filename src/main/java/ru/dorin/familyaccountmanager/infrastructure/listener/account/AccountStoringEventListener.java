package ru.dorin.familyaccountmanager.infrastructure.listener.account;

import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.application.listener.AbstractStoringEventListener;
import ru.dorin.familyaccountmanager.domain.port.EventStore;
import ru.dorin.familyaccountmanager.domain.account.Account;
import ru.dorin.familyaccountmanager.domain.event.account.AccountEvent;

@Component
public class AccountStoringEventListener extends AbstractStoringEventListener<Account, AccountEvent> {

    public AccountStoringEventListener(EventStore<Account, AccountEvent> eventStore) {
        super(eventStore);
    }

    @Override
    public Class<? extends AccountEvent> eventType() {
        return AccountEvent.class;
    }
}
