package ru.dorin.familyaccountmanager.application.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Component
public class EventObjectMapper {

    private final ObjectMapper mapper;
    private final Map<String, Class<? extends DomainEvent<?>>> eventTypeIndex = new HashMap<>();

    public EventObjectMapper(ObjectMapper baseMapper) {
        this.mapper = baseMapper.copy();
        Reflections reflections = new Reflections(DomainEvent.class.getPackage().getName());
        Set<Class<? extends DomainEvent>> eventClasses = reflections.getSubTypesOf(DomainEvent.class);
        for (Class<? extends DomainEvent> eventClass : eventClasses) {
            if (!Modifier.isAbstract(eventClass.getModifiers())) {
                try {
                    eventTypeIndex.put(eventClass.getSimpleName(), (Class<? extends DomainEvent<?>>) eventClass);
                } catch (Exception e) {
                    throw new IllegalStateException("Can't instantiate event class: " + eventClass, e);
                }
            }
        }
    }

    public DomainEvent<?> deserialize(String eventType, String json) {
        Class<? extends DomainEvent<?>> clazz = eventTypeIndex.get(eventType);
        if (clazz == null) {
            throw new IllegalArgumentException("Unknown eventType: " + eventType);
        }

        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Deserialization failed", e);
        }
    }
}