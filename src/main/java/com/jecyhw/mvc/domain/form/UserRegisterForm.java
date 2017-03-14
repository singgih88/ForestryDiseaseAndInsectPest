package com.jecyhw.mvc.domain.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jecyhw on 16-11-9.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterForm {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    private String confirmPassword;
}
