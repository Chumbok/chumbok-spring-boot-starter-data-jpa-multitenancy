package com.chumbok.multitenancy.entity;


import com.chumbok.multitenancy.annotation.OrgIdentifier;
import com.chumbok.multitenancy.annotation.TenantIdentifier;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * Base tenant entity class, provides tenant related attributes
 */
@Getter
@Setter
@MappedSuperclass
@FilterDefs({
        @FilterDef(name = "orgFilter", parameters = @ParamDef(name = "org", type = "string")),
        @FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenant", type = "string"))
})
@Filters({@Filter(name = "orgFilter", condition = "org = :org"),
        @Filter(name = "tenantFilter", condition = "tenant = :tenant")
})
@EntityListeners(TenantEntityListener.class)
public abstract class BaseTenantEntity implements TenantAwareEntity<String> {

    private static final long serialVersionUID = 1L;

    /**
     * Org of persisted entity.
     */
    @OrgIdentifier
    @Column(nullable = false, updatable = false)
    protected String org;

    /**
     * Tenant of persisted entity.
     */
    @TenantIdentifier
    @Column(nullable = false, updatable = false)
    protected String tenant;

}