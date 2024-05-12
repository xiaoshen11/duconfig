package com.bruce.duconfig.client.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2024/5/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigMeta {

    String app;
    String env;
    String ns;
    String configServer;
}
