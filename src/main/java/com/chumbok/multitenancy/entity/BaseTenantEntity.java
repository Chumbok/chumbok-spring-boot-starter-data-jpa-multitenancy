package com.chumbok.multitenancy.entity;


import com.chumbok.multitenancy.TenantEntityListener;
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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

/**
 * Base tenant entity class, provides tenant related attributes
 */
@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@FilterDefs({
        @FilterDef(name = "orgFilter", parameters = @ParamDef(name = "org", type = "string"), defaultCondition = "org = :org"),
        @FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenant", type = "string"), defaultCondition = "tenant = :tenant")
})
@Filters({@Filter(name = "orgFilter"), @Filter(name = "tenantFilter")})
@EntityListeners(TenantEntityListener.class)
public abstract class BaseTenantEntity implements TenantAwareEntity<String> {

    private static final long serialVersionUID = 1L;

    /**
     * Org of persisted entity.
     */
    @OrgIdentifier
    @Column(nullable = false)
    protected String org;

    /**
     * Tenant of persisted entity.
     */
    @TenantIdentifier
    @Column(nullable = false)
    protected String tenant;

}