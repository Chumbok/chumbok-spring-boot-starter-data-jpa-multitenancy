package com.chumbok.multitenancy;

import com.chumbok.multitenancy.entity.TenantAwareEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.util.Assert;

import java.util.Objects;


public class TenantHandler {

    private static MappingContext mappingContext;
    private static TenantAware tenantAware;

    @Autowired
    public void tenantHandler(MappingContext mappingContext) {

        this.mappingContext = mappingContext;
//MappingAuditableBeanWrapperFactory
//        this.createdByProperty = Optional.ofNullable(entity.getPersistentProperty(CreatedBy.class));
//        this.createdDateProperty = Optional.ofNullable(entity.getPersistentProperty(CreatedDate.class));
//        this.lastModifiedByProperty = Optional.ofNullable(entity.getPersistentProperty(LastModifiedBy.class));
//        this.lastModifiedDateProperty = Optional.ofNullable(entity.getPersistentProperty(LastModifiedDate.class));

    }

    public void populateTenantFields(Object obj) {

        System.out.println("---?" + mappingContext.getPersistentEntities());
        System.out.println("");

        //JpaPersistentEntityImpl

        TenantAwareEntity baseTenantEntity = (TenantAwareEntity) obj;
        baseTenantEntity.setOrg((String) tenantAware.getOrg().get());
        baseTenantEntity.setTenant((String) tenantAware.getTenant().get());
        System.out.println("HELLO!" + Objects.toString(obj));
    }


    @Autowired
    public void setTenantAware(TenantAware<?> tenantAware) {
        Assert.notNull(tenantAware, "TenantAware must not be null!");
        this.tenantAware = tenantAware;
    }


}
