package com.bruce.duconfig.client.config;

import org.springframework.core.env.EnumerablePropertySource;

/**
 * @date 2024/5/10
 */
public class DuPropertySource extends EnumerablePropertySource<DuConfigService> {

    public DuPropertySource(String name, DuConfigService source) {
        super(name, source);
    }

    @Override
    public String[] getPropertyNames() {
        return source.getPropertyNames();
    }

    @Override
    public Object getProperty(String name) {
        return source.getProperty(name);
    }
}
