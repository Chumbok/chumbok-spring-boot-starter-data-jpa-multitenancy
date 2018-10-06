package com.chumbok.multitenancy;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class TenantingBeansRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        GenericBeanDefinition tenantEntityListenerBeanDefinition = new GenericBeanDefinition();
        tenantEntityListenerBeanDefinition.setBeanClass(TenantEntityListener.class);
        registry.registerBeanDefinition("tenantEntityListener", tenantEntityListenerBeanDefinition);

        GenericBeanDefinition tenantHandlerBeanDefinition = new GenericBeanDefinition();
        tenantHandlerBeanDefinition.setBeanClass(TenantHandler.class);
        registry.registerBeanDefinition("tenantHandler", tenantHandlerBeanDefinition);

    }

}
