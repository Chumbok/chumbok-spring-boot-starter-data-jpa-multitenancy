package com.chumbok.multitenancy.entity;

/**
 * The interface Tenant aware entity.
 *
 * @param <T> the type parameter
 */
public interface TenantAwareEntity<T> {

    /**
     * Gets org.
     *
     * @return the org
     */
    T getOrg();

    /**
     * Sets org.
     *
     * @param t the t
     */
    void setOrg(T t);

    /**
     * Gets tenant.
     *
     * @return the tenant
     */
    T getTenant();

    /**
     * Sets tenant.
     *
     * @param t the t
     */
    void setTenant(T t);
}
