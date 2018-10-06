package com.chumbok.multitenancy;

import java.util.Optional;

/**
 * Interface for components that are aware of the application's tenant.
 *
 * @param <T> the type of the org tenant identifier instance
 */
public interface TenantAware<T> {

    /**
     * Returns user's org.
     *
     * @return the org
     */
    Optional<T> getOrg();

    /**
     * Returns user's tenant.
     *
     * @return the tenant
     */
    Optional<T> getTenant();
}
