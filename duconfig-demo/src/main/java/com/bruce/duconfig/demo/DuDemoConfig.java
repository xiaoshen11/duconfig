package com.bruce.duconfig.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @date 2024/5/10
 */

@Data
@ConfigurationProperties(prefix = "du")
public class DuDemoConfig {
    String a;
    String b;
}
