package com.bruce.duconfig.client.config;

/**
 * @date 2024/5/10
 */
public interface DuConfigService {

    String[] getPropertyNames();

    String getProperty(String name);
}
