package ru.dorin.familyaccountmanager.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.event.account.AccountEvent;
import ru.dorin.familyaccountmanager.event.budget.BudgetEvent;
import ru.dorin.familyaccountmanager.family.domain.event.FamilyEvent;
import ru.dorin.familyaccountmanager.platform.domain.event.DomainEvent;

import java.lang.reflect.Modifier;
import java.util.Set;

@Component
public class EventObjectMapper {

    private final ObjectMapper mapper;

    public EventObjectMapper(ObjectMapper baseMapper) {
        this.mapper = baseMapper.copy();

        Reflections reflections = new Reflections(
                DomainEvent.class.getPackage().getName(),
                AccountEvent.class.getPackage().getName(),
                BudgetEvent.class.getPackage().getName(),
                FamilyEvent.class.getPackage().getName());
        Set<Class<? extends DomainEvent>> eventClasses = reflections.getSubTypesOf(DomainEvent.class);

        SubtypeResolver resolver = new StdSubtypeResolver();
        for (Class<? extends DomainEvent> eventClass : eventClasses) {
            if (!Modifier.isAbstract(eventClass.getModifiers())) {
                try {
                    resolver.registerSubtypes(new NamedType(eventClass, eventClass.getSimpleName()));
                } catch (Exception e) {
                    throw new IllegalStateException("Can't instantiate event class: " + eventClass, e);
                }
            }
        }

        mapper.setSubtypeResolver(resolver);
        mapper.activateDefaultTypingAsProperty(
                mapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                "@eventType"
        );
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}