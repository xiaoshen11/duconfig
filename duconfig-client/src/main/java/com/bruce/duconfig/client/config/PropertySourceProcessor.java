package com.bruce.duconfig.client.config;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * du property source processor
 * @date 2024/5/11
 */
@Data
public class PropertySourceProcessor implements BeanFactoryPostProcessor, ApplicationContextAware, EnvironmentAware, PriorityOrdered {

    private final static String DU_PROPERTY_SOURCES = "DuPropertySources";
    private final static String DU_PROPERTY_SOURCE = "DuPropertySource";
    Environment environment;
    ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ConfigurableEnvironment ENV = (ConfigurableEnvironment) environment;
        if(ENV.getPropertySources().contains(DU_PROPERTY_SOURCES)) {
            return;
        }
        // 通过http请求去duconfig-server获取配置
        String app = ENV.getProperty("duconfig.app","app1");
        String env = ENV.getProperty("duconfig.env","dev");
        String ns = ENV.getProperty("duconfig.ns","public");
        String configServer = ENV.getProperty("duconfig.configServer","http://localhost:9129");

        ConfigMeta configMeta = new ConfigMeta(app, env, ns, configServer);

        DuConfigService configService = DuConfigService.getDefault(applicationContext, configMeta);
        DuPropertySource propertySource = new DuPropertySource(DU_PROPERTY_SOURCE, configService);
        CompositePropertySource composite = new CompositePropertySource(DU_PROPERTY_SOURCES);
        composite.addPropertySource(propertySource);
        ENV.getPropertySources().addFirst(composite);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
