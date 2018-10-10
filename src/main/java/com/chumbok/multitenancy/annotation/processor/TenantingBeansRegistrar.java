package com.chumbok.multitenancy.annotation.processor;

import com.chumbok.multitenancy.aspect.EnableOrgTenantFiltersAspect;
import com.chumbok.multitenancy.entity.TenantEntityListener;
import com.chumbok.multitenancy.entity.TenantHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Use of @EnableMultitenantJpaRepositories annotation triggers this class.
 * Here in this class, all necessary bean definitions are created that need to be initialized.
 * Create TenantEntityListener, TenantHandler and EnableOrgTenantFiltersAspect bean definitions.
 */
@Slf4j
public class TenantingBeansRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        GenericBeanDefinition tenantEntityListenerBeanDefinition = new GenericBeanDefinition();
        tenantEntityListenerBeanDefinition.setBeanClass(TenantEntityListener.class);
        registry.registerBeanDefinition("tenantEntityListener", tenantEntityListenerBeanDefinition);

        GenericBeanDefinition tenantHandlerBeanDefinition = new GenericBeanDefinition();
        tenantHandlerBeanDefinition.setBeanClass(TenantHandler.class);
        registry.registerBeanDefinition("tenantHandler", tenantHandlerBeanDefinition);

        GenericBeanDefinition tenantAspectBeanDefinition = new GenericBeanDefinition();
        tenantAspectBeanDefinition.setBeanClass(EnableOrgTenantFiltersAspect.class);
        registry.registerBeanDefinition("tenantAspect", tenantAspectBeanDefinition);

        log.info("tenantEntityListener, tenantHandler and tenantAspect bean definitions are created.");

    }

}
