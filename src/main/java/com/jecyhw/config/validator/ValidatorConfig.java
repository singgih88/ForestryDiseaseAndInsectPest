package com.jecyhw.config.validator;

import com.jecyhw.mvc.domain.form.UserRegisterFormValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jecyhw on 16-11-10.
 */
@Configuration
public class ValidatorConfig {

    @Bean
    public UserRegisterFormValidator userRegisterFormValidator() {
        return new UserRegisterFormValidator();
    }
}
