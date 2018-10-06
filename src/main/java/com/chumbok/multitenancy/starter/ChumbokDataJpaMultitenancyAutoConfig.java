package com.chumbok.multitenancy.starter;


import com.chumbok.multitenancy.annotation.EnableJpaTenanting;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableJpaTenanting
public class ChumbokDataJpaMultitenancyAutoConfig {

}