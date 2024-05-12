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

    public String genKey(){
        return this.getApp() + "_" + this.getEnv() + "_" + this.getNs();
    }

    public String listPath(){
        return path("list");
    }

    public String versionPath(){
        return path("version");
    }

    public String path(String context){
        return this.getConfigServer() + "/" + context +"?app=" + this.getApp()
                + "&env=" + this.getEnv() + "&ns=" + this.getNs();
    }
}
