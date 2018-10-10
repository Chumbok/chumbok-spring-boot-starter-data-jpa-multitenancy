package com.chumbok.multitenancy.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.persistence.PrePersist;

/**
 * TenantEntityListener triggers when entity manager about to persist data.
 * A hook to populate Org/Tenant fields.
 */
@Slf4j
public class TenantEntityListener {

    /**
     * Note: The reason behind tenantHandler being static is TenantEntityListener bean loads before TenantHandler.
     * The only way to inject is to make it static.
     */
    private static TenantHandler tenantHandler;

    /**
     * Sets tenant handler when context is loaded.
     */
    @Autowired
    public void tenantHandler(TenantHandler tenantHandler) {
        Assert.notNull(tenantHandler, "TenantHandler must not be null!");
        this.tenantHandler = tenantHandler;
    }

    /**
     * Sets org and tenant identifier on the target object in case it implements {@link TenantAwareEntity} on
     * persist events.
     *
     * @param target the entity class
     */
    @PrePersist
    public void populateOrgTenantField(Object target) {

        Assert.notNull(target, "Entity must not be null!");

        if (tenantHandler != null) {
            tenantHandler.populateOrgTenantFields(target);
        } else {
            log.error("tenantHandler is set to null in com.chumbok.multitenancy.TenantEntityListener.");
        }
    }

}
