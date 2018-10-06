package com.chumbok.multitenancy;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = HelloRepository.class)
@PropertySource("classpath:application.yml")
public class Application {

}





