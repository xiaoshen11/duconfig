package com.bruce.duconfig.client.config;

import com.bruce.duconfig.client.value.SpringValueProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.Optional;

/**
 * register du config bean.
 * @date 2024/5/11
 */
@Slf4j
public class DuConfigRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerClass(registry, PropertySourceProcessor.class);
        registerClass(registry, SpringValueProcessor.class);

    }

    private static void registerClass(BeanDefinitionRegistry registry, Class<?> aClass) {
        log.info("register " + aClass.getName());
        Optional<String> first = Arrays.stream(registry.getBeanDefinitionNames())
                .filter(x -> aClass.getName().equals(x)).findFirst();

        if (first.isPresent()) {
            log.info(aClass.getName() + " already registered");
            return;
        }

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.
                genericBeanDefinition(aClass).getBeanDefinition();
        registry.registerBeanDefinition(aClass.getName(), beanDefinition);
    }
}
