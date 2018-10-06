package com.chumbok.multitenancy.entity;

public interface TenantAwareEntity<T> {

    T getOrg();

    void setOrg(T t);

    T getTenant();

    void setTenant(T t);
}
