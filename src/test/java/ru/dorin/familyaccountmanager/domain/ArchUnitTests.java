package ru.dorin.familyaccountmanager.domain;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import ru.dorin.familyaccountmanager.domain.aggregate.AbstractDomainAggregate;
import ru.dorin.familyaccountmanager.domain.event.DomainEvent;
import ru.dorin.familyaccountmanager.domain.port.EventStore;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class ArchUnitTests {

    @Test
    void all_domain_events_should_be_in_domain_event_package() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.dorin.familyaccountmanager");
        ArchRule rule = classes()
                .that().areAssignableTo(DomainEvent.class)
                .should().resideInAnyPackage(DomainEvent.class.getPackage().getName());

        rule.check(importedClasses);
    }

    @Test
    void in_domain_event_package_only_domain_events() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.dorin.familyaccountmanager");
        ArchRule rule = classes()
                .that().resideInAnyPackage(DomainEvent.class.getPackage().getName())
                .should().beAssignableTo(DomainEvent.class);

        rule.check(importedClasses);
    }

    @Test
    void domain_events_only_in_domain_event_package() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.dorin.familyaccountmanager");
        ArchRule rule = classes()
                .that().resideOutsideOfPackage(DomainEvent.class.getPackage().getName())
                .should().notBeAssignableTo(DomainEvent.class);

        rule.check(importedClasses);
    }

    @Test
    void all_aggregates_should_be_in_aggregate_package() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.dorin.familyaccountmanager");
        ArchRule rule = classes()
                .that().areAssignableTo(AbstractDomainAggregate.class)
                .should().resideInAnyPackage(AbstractDomainAggregate.class.getPackage().getName());

        rule.check(importedClasses);
    }

    @Test
    void all_event_stores_should_be_annotated_with_property() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.dorin.familyaccountmanager");
        ArchRule rule = classes()
                .that().areAssignableTo(EventStore.class)
                .and().areNotInterfaces()
                .and().doNotHaveModifier(JavaModifier.ABSTRACT)
                .should().beAnnotatedWith(ConditionalOnProperty.class);

    }

}