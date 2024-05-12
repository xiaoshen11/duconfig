package com.bruce.duconfig.client.repository;

import com.bruce.duconfig.client.config.ConfigMeta;

import java.util.Map;

/**
 * @date 2024/5/12
 */
public interface DuRepositoryChangeListener {

    void onChange(ChangeEvent event);

    record ChangeEvent(ConfigMeta meta, Map<String, String> config) {}
}
