package com.bruce.duconfig.client.annotation;

import com.bruce.duconfig.client.config.DuConfigRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Du config client entrypoint.
 * @date 2024/5/10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Import({DuConfigRegistrar.class})
public @interface EnableDuConfig {
}
