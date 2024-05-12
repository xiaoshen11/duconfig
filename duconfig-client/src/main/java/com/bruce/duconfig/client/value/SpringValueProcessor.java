package com.bruce.duconfig.client.value;

import cn.kimmking.utils.FieldUtils;
import com.bruce.duconfig.client.util.PlaceholderHelper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * process spring value
 * 1.扫描所有的spring value，保存起来
 * 2.配置变更时，更新所有的spring value
 * @date 2024/5/12
 */
@Slf4j
public class SpringValueProcessor implements BeanPostProcessor, BeanFactoryAware, ApplicationListener<EnvironmentChangeEvent> {

    final static PlaceholderHelper helper = PlaceholderHelper.getInstance();
    final static MultiValueMap<String, SpringValue> VALUE_HOLDER = new LinkedMultiValueMap<>();
    @Setter
    private BeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 第一步
        List<Field> fields = FieldUtils.findAnnotatedField(bean.getClass(), Value.class);
        fields.forEach(field -> {
            log.info("[DUCONFIG] ===>> find spring value :{}", field);
            Value value = field.getAnnotation(Value.class);
            helper.extractPlaceholderKeys(value.value()).forEach(
                    key -> {
                        log.info("[DUCONFIG] >> find spring value: {}", key);
                        SpringValue springValue = new SpringValue(bean, beanName, key, value.value(), field);
                        VALUE_HOLDER.add(key, springValue);
                    }
            );
        });
        return bean;
    }

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        // 第二步
        log.info("[DUCONFIG] >> update spring value for keys: {}", event.getKeys());
        event.getKeys().forEach(key -> {
            log.info("[DUCONFIG] ===>> update spring value :{}", key);
            List<SpringValue> springValues = VALUE_HOLDER.get(key);
            if(springValues == null || springValues.isEmpty()){
                return;
            }
            springValues.forEach(springValue -> {
                log.info("[DUCONFIG] ===>> update spring value :{} for key {}", springValue, key);
                try {
                    Object value = helper.resolvePropertyValue((ConfigurableBeanFactory) beanFactory,
                            springValue.getBeanName(), springValue.getPlaceholder());
                    log.info("[DUCONFIG] >> update value: {} for holder {}", value, springValue.getPlaceholder());
                    springValue.getField().setAccessible(true);
                    springValue.getField().set(springValue.getBean(),value);
                }catch (Exception e){
                    log.error("[DUCONFIG] ===>> update spring value error", e);
                }
            });
        });
    }
}
