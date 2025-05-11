package ru.dorin.familyaccountmanager.infrastructure.listener.account;

import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.platform.domain.listener.AbstractStoringEventListener;
import ru.dorin.familyaccountmanager.platform.domain.port.EventStore;
import ru.dorin.familyaccountmanager.account.Account;
import ru.dorin.familyaccountmanager.event.account.AccountEvent;
import ru.dorin.familyaccountmanager.event.account.AccountTransactionEvent;

@Component
public class AccountStoringTransactionEventListener extends AbstractStoringEventListener<Account, AccountEvent> {

    public AccountStoringTransactionEventListener(EventStore<Account, AccountEvent> eventStore) {
        super(eventStore);
    }

    @Override
    public Class<? extends AccountEvent> eventType() {
        return AccountTransactionEvent.class;
    }
}
