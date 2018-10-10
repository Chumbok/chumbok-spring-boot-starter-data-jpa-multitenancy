package com.chumbok.multitenancy.entity;

import com.chumbok.multitenancy.TenantAware;
import com.chumbok.multitenancy.entity.TenantAwareEntity;
import com.chumbok.multitenancy.exception.TenantAwareBeanNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;


/**
 * TenantHandler populate Org/Tenant fields of an Entity.
 */
@Slf4j
public class TenantHandler {

    /**
     * Note: The reason behind tenantAware being static is TenantHandler bean loads before TenantAware.
     * The only way to inject is to make it static.
     */
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
            log.error("tenantAware is set to null in TenantHandler.");
            throw new TenantAwareBeanNotFound("tenantAware bean is not defined.");
        }

        if (!tenantAware.getOrg().isPresent()) {
            log.error("tenantAware org is set to null in TenantHandler.");
            return;
        }

        if (!tenantAware.getTenant().isPresent()) {
            log.error("tenantAware tenant is set to null in TenantHandler.");
            return;
        }

        if (obj instanceof TenantAwareEntity) {
            TenantAwareEntity tenantAwareEntity = (TenantAwareEntity) obj;
            tenantAwareEntity.setOrg(tenantAware.getOrg().get());
            tenantAwareEntity.setTenant(tenantAware.getTenant().get());
            log.error("Persistent entity's Org/Tenant fields are populated.");
        } else {
            log.error("Persistent entity did not implement TenantAwareEntity.");
        }

    }

}
