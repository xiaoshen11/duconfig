package com.bruce.duconfig.client.config;

import com.bruce.duconfig.client.repository.DuRepositoryChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @date 2024/5/11
 */
@Slf4j
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
        Set<String> keys = calcChangeKeys(this.config,event.config());
        this.config = event.config();
        if(keys.isEmpty()){
            log.info("[DUCONFIG] calcChangeKeys return empty, ignore update.");
        }
        if(config != null){
            log.info("[DUCONFIG] fire an EnvironmentChangeEvent with keys: " + keys);
            applicationContext.publishEvent(new EnvironmentChangeEvent(keys));

        }
    }

    private Set<String> calcChangeKeys(Map<String, String> oldConfigs, Map<String, String> newConfigs) {
        if(oldConfigs.isEmpty()){
            return newConfigs.keySet();
        }
        if(newConfigs.isEmpty()){
            return oldConfigs.keySet();
        }
        Set<String> news = newConfigs.keySet().stream()
                .filter(key -> !newConfigs.get(key).equals(oldConfigs.get(key)))
                .collect(Collectors.toSet());
        oldConfigs.keySet().stream().filter(key -> !newConfigs.containsKey(key)).forEach(news::add);
        return news;
    }
}
