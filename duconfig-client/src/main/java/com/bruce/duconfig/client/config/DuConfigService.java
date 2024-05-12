package com.bruce.duconfig.client.config;

import com.bruce.duconfig.client.repository.DuRepository;

/**
 * @date 2024/5/10
 */
public interface DuConfigService {

    static DuConfigService getDefault(ConfigMeta meta){
        DuRepository repository = DuRepository.getDefault(meta);
        return new DuConfigServiceImpl(repository.getConfig());
    }

    String[] getPropertyNames();

    String getProperty(String name);
}
