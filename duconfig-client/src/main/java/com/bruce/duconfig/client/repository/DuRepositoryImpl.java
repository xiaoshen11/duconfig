package com.bruce.duconfig.client.repository;

import cn.kimmking.utils.HttpUtils;
import com.alibaba.fastjson.TypeReference;
import com.bruce.duconfig.client.config.ConfigMeta;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * default impl for du repository
 * @date 2024/5/12
 */
@AllArgsConstructor
public class DuRepositoryImpl implements DuRepository {

    ConfigMeta meta;

    @Override
    public Map<String, String> getConfig() {
        String listPath = meta.getConfigServer() + "/list?app=" + meta.getApp()
                + "&env=" + meta.getEnv() + "&ns=" + meta.getNs();
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
}
