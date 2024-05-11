package com.bruce.duconfig.client.config;

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
public class DuConfigRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        System.out.println("register PropertySourceProcessor");
        Optional<String> first = Arrays.stream(registry.getBeanDefinitionNames())
                .filter(x -> PropertySourceProcessor.class.getName().equals(x)).findFirst();

        if (first.isPresent()) {
            System.out.println("PropertySourceProcessor already registered");
            return;
        }

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.
                genericBeanDefinition(PropertySourceProcessor.class).getBeanDefinition();
        registry.registerBeanDefinition(PropertySourceProcessor.class.getName(), beanDefinition);

    }
}
