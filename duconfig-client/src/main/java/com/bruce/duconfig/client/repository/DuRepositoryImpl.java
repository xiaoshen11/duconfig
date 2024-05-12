package com.bruce.duconfig.client.repository;

import cn.kimmking.utils.HttpUtils;
import com.alibaba.fastjson.TypeReference;
import com.bruce.duconfig.client.config.ConfigMeta;
import lombok.extern.slf4j.Slf4j;

import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * default impl for du repository
 * @date 2024/5/12
 */
@Slf4j
public class DuRepositoryImpl implements DuRepository {

    ConfigMeta meta;
    Map<String, Long> versionMap = new HashMap<>();
    Map<String, Map<String, String>> configMap = new HashMap<>();
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    List<DuRepositoryChangeListener> listeners = new ArrayList<>();

    public DuRepositoryImpl(ConfigMeta meta) {
        this.meta = meta;
        executor.scheduleWithFixedDelay(this::heartbeat, 1000, 5000, TimeUnit.MILLISECONDS);
    }

    public void addListener(DuRepositoryChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public Map<String, String> getConfig() {
        String key = meta.genKey();
        if(configMap.containsKey(key)){
            return configMap.get(key);
        }
        Map<String, String> resultMap = findAll();

        return resultMap;
    }

    private Map<String, String> findAll() {
        String listPath = meta.listPath();
        log.info("[DUCONFIG] list all configs from du config server");
        List<Configs> configs = HttpUtils.httpGet(listPath, new TypeReference<List<Configs>>() {
        });
        Map<String, String> resultMap = new HashMap<>();
        if (configs != null) {
            for (Configs config : configs) {
                resultMap.put(config.getPkey(), config.getPval());
            }
        }
        return resultMap;
    }

    public void heartbeat() {
        String versionPath = meta.versionPath();

        Long version = HttpUtils.httpGet(versionPath, Long.class);
        String key = meta.genKey();
        Long oldVersion = versionMap.getOrDefault(key,-1L);
        if(version > oldVersion){ // 发生了变化
            log.info("[DUCONFIG] current=" + version + ", oldVersion=" + oldVersion);
            log.info("[DUCONFIG] need update new configs");
            versionMap.put(key, version);
            Map<String, String> newconfigs = this.findAll();
            configMap.put(key,newconfigs);

            listeners.forEach(l -> l.onChange(new DuRepositoryChangeListener.ChangeEvent(meta, newconfigs)));
        }
    }

}
