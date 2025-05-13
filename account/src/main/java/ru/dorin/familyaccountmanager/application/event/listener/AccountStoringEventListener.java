package ru.dorin.familyaccountmanager.application.event.listener;

import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.domain.aggregate.Account;
import ru.dorin.familyaccountmanager.domain.event.AccountEvent;
import ru.dorin.familyaccountmanager.domain.listener.AbstractStoringEventListener;
import ru.dorin.familyaccountmanager.domain.port.EventStore;

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
