package com.chumbok.multitenancy;

import com.chumbok.multitenancy.annotation.EnableMultitenantJpaRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@EnableMultitenantJpaRepositories
public class JpaTenantTestConfig {

    @Bean
    public TenantAware<String> tenantAware() {
        return new TenantAwareImpl();
    }

    static class TenantAwareImpl implements TenantAware<String> {

        private String user;

        public void userIdentifier(String user) {
            this.user = user;
        }

        @Override
        public Optional<String> getOrg() {
            return Optional.of(user + "Org");
        }

        @Override
        public Optional<String> getTenant() {
            return Optional.of(user + "Tenant");
        }
    }
}