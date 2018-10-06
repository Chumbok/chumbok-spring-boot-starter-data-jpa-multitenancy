package com.chumbok.multitenancy;

import com.chumbok.multitenancy.annotation.EnableJpaTenanting;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Optional;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableJpaTenanting
public class JpaTenantTestConfig {

    @Bean
    public TenantAware<String> tenantAware() {
        return new TenantAwareImpl();
    }

    static class TenantAwareImpl implements TenantAware<String> {

        @Override
        public Optional<String> getOrg() {
            return Optional.of("TestOrg");
        }

        @Override
        public Optional<String> getTenant() {
            return Optional.of("TestTenant");
        }
    }
}