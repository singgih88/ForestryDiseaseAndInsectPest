package com.jecyhw.config.deepzoom;

import com.jecyhw.core.pivotviewer.DeepZoomFileTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jecyhw on 16-11-21.
 */
@Configuration
@EnableConfigurationProperties(DeepZoomFileProperties.class)
public class DeepZoomConfig {
    private final DeepZoomFileProperties deepZoomFileProperties;

    public DeepZoomConfig(DeepZoomFileProperties deepZoomFileProperties) {
        this.deepZoomFileProperties = deepZoomFileProperties;
    }

    @Bean
    public DeepZoomFileTemplate deepZoomFileTemplate() {
        return deepZoomFileProperties.createDeepZoomFileTemplate();
    }
}
