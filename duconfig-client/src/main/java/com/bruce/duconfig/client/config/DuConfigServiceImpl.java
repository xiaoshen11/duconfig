package com.bruce.duconfig.client.config;

import com.bruce.duconfig.client.repository.DuRepositoryChangeListener;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @date 2024/5/11
 */
public class DuConfigServiceImpl implements DuConfigService {

    ApplicationContext applicationContext;
    Map<String, String> config;

    public DuConfigServiceImpl(ApplicationContext applicationContext, Map<String, String> config) {
        this.applicationContext = applicationContext;
        this.config = config;
    }

    @Override
    public String[] getPropertyNames() {
        return this.config.keySet().toArray(new String[0]);
    }

    @Override
    public String getProperty(String name) {
        return this.config.get(name);
    }

    @Override
    public void onChange(ChangeEvent event) {
        this.config = event.config();
        if(config != null){
            System.out.println("[DUCONFIG] fire an EnvironmentChangeEvent with keys: " + config.keySet());
            applicationContext.publishEvent(new EnvironmentChangeEvent(config.keySet()));
        }
    }
}
