package com.jecyhw.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by jecyhw on 17-2-13.
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {
    @Value("${app.name}")
    private String name;

    @Value("${app.alias}")
    private String alias;
}
