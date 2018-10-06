package com.chumbok.multitenancy.entity;


import com.chumbok.multitenancy.TenantEntityListener;
import com.chumbok.multitenancy.annotation.OrgIdentifier;
import com.chumbok.multitenancy.annotation.TenantIdentifier;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * Base tenant entity class, provides tenant related attributes
 */
@Getter
@Setter
@MappedSuperclass
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