package com.chumbok.multitenancy;

import com.chumbok.multitenancy.entity.BaseTenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
public class Hello extends BaseTenantEntity {

    @Id
    private String id;

    @Column
    private String message;

}