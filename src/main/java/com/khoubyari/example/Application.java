package com.khoubyari.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
 * Spring Boot main Application class. It configures Spring Boot, configuration scanner, JPA.
 */

@SpringBootApplication
@Configuration
@EnableAutoConfiguration  // Spring Boot Auto Configuration
@ComponentScan(basePackages = "com.khoubyari.example")
@EnableJpaRepositories("com.khoubyari.example.dao.jpa") // JPA repository
public class Application extends SpringBootServletInitializer {

    private static final Class<Application> applicationClass = Application.class;
   // private static final Logger LOGGER = LoggerFactory.getLogger(applicationClass);

	public static void main(String[] args) {
		SpringApplication.run(applicationClass, args);
	}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

}
