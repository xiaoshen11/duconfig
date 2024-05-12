package com.bruce.duconfig.client.config;

import com.bruce.duconfig.client.repository.DuRepository;
import com.bruce.duconfig.client.repository.DuRepositoryChangeListener;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @date 2024/5/10
 */
public interface DuConfigService extends DuRepositoryChangeListener {

    static DuConfigService getDefault(ApplicationContext applicationContext, ConfigMeta meta){
        DuRepository repository = DuRepository.getDefault(meta);
        Map<String, String> config = repository.getConfig();
        DuConfigService configService = new DuConfigServiceImpl(applicationContext, config);
        repository.addListener(configService);
        return configService;
    }

    String[] getPropertyNames();

    String getProperty(String name);
}
