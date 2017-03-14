package com.jecyhw.config.imageserver;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

/**
 * Created by jecyhw on 16-11-23.
 */
@ConfigurationProperties(prefix = "pest-image-server")
public class ImageServerProperties {
    /**
     * 图片服务器的根目录
     */
    @NotNull
    private String basePath;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
