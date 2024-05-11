package com.bruce.duconfig.client.config;

import java.util.Map;

/**
 * @date 2024/5/11
 */
public class DuConfigServiceImpl implements DuConfigService {

    Map<String, String> config;

    public DuConfigServiceImpl(Map<String, String> config) {
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
}
