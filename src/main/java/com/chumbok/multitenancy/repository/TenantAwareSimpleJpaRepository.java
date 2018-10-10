package com.chumbok.multitenancy.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * SimpleJpaRepository pass org and tenant as where clause with queries by default, except findById method.
 * This class override findById method and use findOne instead with spec.
 * <p>
 * See: https://stackoverflow.com/questions/42173894/hibernate-enablefilter-not-working-when-loading-entity-by-id
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 */
public class TenantAwareSimpleJpaRepository<T, ID> extends SimpleJpaRepository<T, ID> implements JpaRepository<T, ID> {

    /**
     * Instantiates a new Tenant aware simple jpa repository.
     *
     * @param entityInformation the entity information
     * @param entityManager     the entity manager
     */
    public TenantAwareSimpleJpaRepository(JpaEntityInformation entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    /**
     * Instantiates a new Tenant aware simple jpa repository.
     *
     * @param domainClass the domain class
     * @param em          the em
     */
    public TenantAwareSimpleJpaRepository(Class domainClass, EntityManager em) {
        super(domainClass, em);
    }

    /**
     * Override findById method.
     *
     * @param id
     * @return
     */
    @Override
    public Optional<T> findById(ID id) {
        return super.findOne((Specification) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id));
    }

}
