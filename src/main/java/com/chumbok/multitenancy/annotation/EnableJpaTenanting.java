package com.chumbok.multitenancy.annotation;

import com.chumbok.multitenancy.TenantingBeansRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to enable tenanting in JPA via annotation configuration.
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(TenantingBeansRegistrar.class)
public @interface EnableJpaTenanting {

}
