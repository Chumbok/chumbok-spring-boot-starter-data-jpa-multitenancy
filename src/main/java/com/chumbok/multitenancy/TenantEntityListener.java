package com.chumbok.multitenancy;

import com.chumbok.multitenancy.entity.TenantAwareEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.persistence.PrePersist;

/**
 * TenantEntityListener triggers when entity manager about to persist data.
 * A hook to populate Org/Tenant fields.
 */
public class TenantEntityListener {

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
     * @param target the target
     */
    @PrePersist
    public void populateOrgTenantField(Object target) {
        Assert.notNull(target, "Entity must not be null!");
        if (tenantHandler != null) {
            tenantHandler.populateOrgTenantFields(target);
        }
    }

}
