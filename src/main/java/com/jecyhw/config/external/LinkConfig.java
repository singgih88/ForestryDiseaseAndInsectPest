package com.jecyhw.config.external;

import com.jecyhw.config.deepzoom.DeepZoomFileProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jecyhw on 17-2-13.
 */
@Configuration
@EnableConfigurationProperties(LinkProperties.class)
public class LinkConfig {

    private final LinkProperties linkProperties;

    public LinkConfig(LinkProperties linkProperties) {
        this.linkProperties = linkProperties;
    }

    public String getLink(String type) {
        switch (type) {
            case "animal" :
                return linkProperties.getAnimal();
            case "plant" :
                return linkProperties.getPlant();
            case "gis" :
                return linkProperties.getGis();
            case "visualization" :
                return linkProperties.getVisualization();
            case "insect" :
                return linkProperties.getInsect();
        }
        return "";
    }
}
