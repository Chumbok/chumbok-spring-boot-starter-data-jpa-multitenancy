package com.chumbok.multitenancy.aspect;

import com.chumbok.multitenancy.TenantAware;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.EntityManager;

/**
 * Spring Aspect to enable orgFilter and tenantFilter defined in JPA Entity classes.
 * Also set parameters defined in filters from TenantAware bean.
 */
@Slf4j
@Aspect
public class EnableOrgTenantFiltersAspect {

    private static final String ORG_FILTER = "orgFilter";
    private static final String TENANT_FILTER = "tenantFilter";

    private TenantAware tenantAware;

    /**
     * Instantiates a new Enable org tenant filters aspect with TenantAware.
     *
     * @param tenantAware the tenant aware
     */
    public EnableOrgTenantFiltersAspect(TenantAware<?> tenantAware) {
        this.tenantAware = tenantAware;
    }

    /**
     * Gets session after.
     *
     * @param joinPoint the join point
     * @param retVal    the ret val
     */
    @AfterReturning(pointcut = "bean(entityManagerFactory) && execution(* createEntityManager(..))", returning = "retVal")
    public void getSessionAfter(JoinPoint joinPoint, Object retVal) {

        if (retVal == null || !EntityManager.class.isInstance(retVal)) {
            logDisabledFiltersInfo();
            return;
        }

        if (tenantAware == null) {
            log.error("tenantAware is set to null in com.chumbok.multitenancy.aspect.EnableOrgTenantFiltersAspect.");
            logDisabledFiltersInfo();
            return;
        }

        if (!tenantAware.getOrg().isPresent()) {
            log.error("tenantAware org is set to null in com.chumbok.multitenancy.aspect.EnableOrgTenantFiltersAspect.");
            logDisabledFiltersInfo();
            return;
        }

        if (!tenantAware.getTenant().isPresent()) {
            log.error("tenantAware tenant is set to null in com.chumbok.multitenancy.aspect.EnableOrgTenantFiltersAspect.");
            logDisabledFiltersInfo();
            return;
        }

        Session session = ((EntityManager) retVal).unwrap(Session.class);

        Filter orgFilter = null;
        try {
            orgFilter = session.enableFilter(ORG_FILTER);
        } catch (HibernateException ex) {
            log.error("'orgFilter' not defined in Entity. Entity should inherit/extend BaseTenantEntity class.");
        }

        Filter tenantFilter = null;
        try {
            tenantFilter = session.enableFilter(TENANT_FILTER);
        } catch (HibernateException ex) {
            log.error("'tenantFilter' not defined in Entity. Entity should inherit/extend BaseTenantEntity class.");
        }

        if (orgFilter == null && tenantFilter != null) {
            session.disableFilter(TENANT_FILTER);
            log.error("Disabled tenantFilter since orgFilter is not defined in Entity class.");
        }

        if (orgFilter != null && tenantFilter == null) {
            session.disableFilter(ORG_FILTER);
            log.error("Disabled orgFilter since tenantFilter is not defined in Entity class.");
        }

        if (orgFilter != null && tenantFilter != null) {
            orgFilter.setParameter("org", tenantAware.getOrg().get());
            tenantFilter.setParameter("tenant", tenantAware.getTenant().get());
            log.info("orgFilter and tenantFilter is enabled.");
        } else {
            logDisabledFiltersInfo();
        }


    }

    private void logDisabledFiltersInfo() {
        log.info("orgFilter and tenantFilter is disabled.");
    }

}
