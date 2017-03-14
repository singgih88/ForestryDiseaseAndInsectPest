package com.jecyhw.config.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by jecyhw on 17-2-13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "external.link")
public class LinkProperties {
    private String plant = "";
    private String animal = "";
    private String gis = "";
    private String visualization = "";
    private String insect = "";
}
