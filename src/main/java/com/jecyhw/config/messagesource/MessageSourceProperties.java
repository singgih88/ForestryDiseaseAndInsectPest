package com.jecyhw.config.messagesource;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

/**
 * Created by jecyhw on 16-11-30.
 */
@ConfigurationProperties(prefix = "i18n.message")
public class MessageSourceProperties {
    /**
     * i18 properties location in resources
     */
    @NotNull
    private String[] baseNames;

    public String[] getBaseNames() {
        return baseNames;
    }

    public void setBaseNames(String[] baseNames) {
        this.baseNames = baseNames;
    }
}
