package ru.dorin.familyaccountmanager.application.event.listener.integration.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.application.event.listener.integration.IntegrationEventListenerRegistry;
import ru.dorin.familyaccountmanager.application.exception.IntegrationEventListenerNotFoundException;
import ru.dorin.familyaccountmanager.integration.event.IntegrationEvent;
import ru.dorin.familyaccountmanager.integration.event.listener.IntegrationEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.kafka.enabled", havingValue = "true")
public class DefaultIntegrationEventKafkaListener {

    private final IntegrationEventListenerRegistry integrationEventListenerRegistry;

    @KafkaListener(topics = "${application.kafka.topic.integration-event}", groupId = "${spring.application.name}")
    public void listen(IntegrationEvent event) {
        log.info("Received integration event: {}", event);
        IntegrationEventListener<IntegrationEvent> listener = integrationEventListenerRegistry.getListenerFor(event);
        if (listener == null) {
            throw new IntegrationEventListenerNotFoundException();
        }
        listener.handle(event);
    }
}
