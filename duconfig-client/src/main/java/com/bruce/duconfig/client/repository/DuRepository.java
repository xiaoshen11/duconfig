package com.bruce.duconfig.client.repository;

import com.bruce.duconfig.client.config.ConfigMeta;

import java.util.Map;

/**
 * interface to get config from remote
 * @date 2024/5/12
 */
public interface DuRepository {

    static DuRepository getDefault(ConfigMeta meta) {
        return new DuRepositoryImpl(meta);
    }

    Map<String,String> getConfig();

    void addListener(DuRepositoryChangeListener listener);

}
