package com.jecyhw.config.deepzoom;

import com.jecyhw.core.pivotviewer.DeepZoomFileTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jecyhw on 16-11-21.
 */
@ConfigurationProperties(prefix = "pivotviewer.deepzoom")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeepZoomFileProperties {

    /**
     * deepzoom file's rootPath
     */
    private String rootPath = "/var/diseasePest";
    private int tileSize = 256;
    private int overlap = 1;
    private String tileFormat = "png";
    private String descriptorExt = "xml";
    private int maxLevel = 0;


    public DeepZoomFileTemplate createDeepZoomFileTemplate() {
        return new DeepZoomFileTemplate(getRootPath(), getTileSize(), getOverlap(), getTileFormat(), getDescriptorExt());
    }

    public Map<String, Object> createFreemarkerVariables() {
        Map<String, Object> result = new HashMap<>();
        result.put("tileSize", getTileSize());
        result.put("tileFormat", getTileFormat());
        result.put("maxLevel", getMaxLevel());
        return result;
    }
}
