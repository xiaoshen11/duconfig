package com.bruce.duconfig.client.config;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * du property source processor
 * @date 2024/5/11
 */
@Data
public class PropertySourceProcessor implements BeanFactoryPostProcessor, EnvironmentAware, PriorityOrdered {

    private final static String DU_PROPERTY_SOURCES = "DuPropertySources";
    private final static String DU_PROPERTY_SOURCE = "DuPropertySource";
    Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ConfigurableEnvironment env = (ConfigurableEnvironment) environment;
        if(env.getPropertySources().contains(DU_PROPERTY_SOURCES)) {
            return;
        }
        // 通过http请求去duconfig-server获取配置
        Map<String, String> config = new HashMap<>();
        config.put("du.a","dev500");
        config.put("du.b","b600");
        config.put("du.c","c700");
        DuConfigService configService = new DuConfigServiceImpl(config);
        DuPropertySource propertySource = new DuPropertySource(DU_PROPERTY_SOURCE, configService);
        CompositePropertySource composite = new CompositePropertySource(DU_PROPERTY_SOURCES);
        composite.addPropertySource(propertySource);
        env.getPropertySources().addFirst(composite);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
