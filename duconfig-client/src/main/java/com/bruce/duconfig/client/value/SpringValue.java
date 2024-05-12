package com.bruce.duconfig.client.value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

/**
 * @date 2024/5/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpringValue {

    private Object bean;
    private String beanName;
    private String key;
    private String placeholder;
    private Field field;

}
