package com.chumbok.multitenancy;

import com.chumbok.multitenancy.entity.BaseTenantEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
public class Hello extends BaseTenantEntity {

    @Id
    private String id;

    @Column
    private String message;

}