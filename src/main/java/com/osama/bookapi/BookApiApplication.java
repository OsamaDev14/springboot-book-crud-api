package com.osama.bookapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point of the Spring Boot application.
 *
 * @SpringBootApplication = combination of:
 * 1. @Configuration → defines configuration class
 * 2. @EnableAutoConfiguration → auto configures Spring Boot
 * 3. @ComponentScan → Scans for components (Controllers, Services, Repositories)
 */
@SpringBootApplication
public class BookApiApplication {
    public static void main(String[] args) {

        // Creates application context and starts embedded server (Tomcat)
        SpringApplication.run(BookApiApplication.class, args);
    }
}