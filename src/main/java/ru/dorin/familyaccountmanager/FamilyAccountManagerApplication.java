package ru.dorin.familyaccountmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FamilyAccountManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FamilyAccountManagerApplication.class, args);
    }

}
