package com.chumbok.multitenancy.exception;

/**
 * Custom exception when TenantAware bean not found in Application Context.
 */
public class TenantAwareBeanNotFound extends RuntimeException {

    /**
     * Instantiates a new Tenant aware bean not found.
     *
     * @param message the message
     */
    public TenantAwareBeanNotFound(String message) {
        super(message);
    }
}
