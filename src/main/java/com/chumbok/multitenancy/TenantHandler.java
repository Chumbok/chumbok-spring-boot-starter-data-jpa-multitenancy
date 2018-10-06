package com.chumbok.multitenancy;

import com.chumbok.multitenancy.entity.TenantAwareEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;


/**
 * TenantHandler populate Org/Tenant fields of an Entity.
 */
@Slf4j
public class TenantHandler {

    private static TenantAware tenantAware;

    /**
     * Sets tenant aware when context is loaded.
     */
    @Autowired
    public void tenantAware(TenantAware<?> tenantAware) {
        Assert.notNull(tenantAware, "TenantAware must not be null!");
        this.tenantAware = tenantAware;
    }

    /**
     * Populate Org/Tenant fields.
     *
     * @param obj the obj
     */
    protected void populateOrgTenantFields(Object obj) {

        if (tenantAware == null) {
            log.debug("tenantAware is set to null in TenantHandler.");
            return;
        }

        if (obj instanceof TenantAwareEntity) {
            TenantAwareEntity tenantAwareEntity = (TenantAwareEntity) obj;
            tenantAwareEntity.setOrg(tenantAware.getOrg().get());
            tenantAwareEntity.setTenant(tenantAware.getTenant().get());
            log.debug("Persistent entity's Org/Tenant fields are populated.");
        } else {
            log.debug("Persistent entity did not implement TenantAwareEntity.");
        }

    }

}
