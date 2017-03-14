package com.jecyhw.config.freemarker;

import com.jecyhw.config.deepzoom.DeepZoomFileProperties;
import com.jecyhw.core.freemarker.Freemarker;
import freemarker.template.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

/**
 * Created by jecyhw on 16-11-9.
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(DeepZoomFileProperties.class)
public class NoWebFreemarkerConfig {

    private final DeepZoomFileProperties deepZoomFileProperties;

    public NoWebFreemarkerConfig(DeepZoomFileProperties deepZoomFileProperties) {
        this.deepZoomFileProperties = deepZoomFileProperties;
    }

    @Bean
    public Freemarker freemarker(Configuration configuration) {
        return new Freemarker(configuration);
    }

    @Bean
    public FreeMarkerConfigurationFactoryBean configuration() {
        FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean = new FreeMarkerConfigurationFactoryBean();
        freeMarkerConfigurationFactoryBean.setTemplateLoaderPath("classpath:freemarker");
        freeMarkerConfigurationFactoryBean.setConfigLocation(new ClassPathResource("freemarker.properties"));
        freeMarkerConfigurationFactoryBean.setFreemarkerVariables(deepZoomFileProperties.createFreemarkerVariables());
        return freeMarkerConfigurationFactoryBean;
    }

}
