package com.chumbok.multitenancy;

import com.chumbok.multitenancy.entity.TenantAwareEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.persistence.PrePersist;

public class TenantEntityListener {

    private static TenantHandler tenantHandler;

    @Autowired
    public void setTenantHandler(TenantHandler tenantHandler) {
        Assert.notNull(tenantHandler, "TenantHandler must not be null!");
        this.tenantHandler = tenantHandler;
    }

    /**
     * Sets org and tenant identifier on the target object in case it implements {@link TenantAwareEntity} on
     * persist events.
     *
     * @param target
     */
    @PrePersist
    public void populateOrgTenantField(Object target) {

        Assert.notNull(target, "Entity must not be null!");

        if (tenantHandler != null) {
            tenantHandler.populateTenantFields(target);
        }

    }

}
