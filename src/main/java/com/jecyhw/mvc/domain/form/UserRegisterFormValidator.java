package com.jecyhw.mvc.domain.form;

import com.jecyhw.core.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;


/**
 * Created by jecyhw on 16-11-10.
 */
public class UserRegisterFormValidator implements Validator {
    private static final Pattern userNamePattern = Pattern.compile("^[0-9A-Za-z]{3,20}$");
    private static final Pattern passwordPattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegisterForm userRegisterForm = (UserRegisterForm) target;
        String userName = userRegisterForm.getUserName();
        if (ObjectUtils.isNull(userName) || !userNamePattern.matcher(userName).matches()) {
            errors.rejectValue("userName", "userName.matchFail", "输入不符合规则");
        }

        String password = userRegisterForm.getPassword();
        if (ObjectUtils.isNull(password) || !passwordPattern.matcher(password).matches()) {
            errors.rejectValue("password", "password.matchFail", "输入不符合规则");
        }

        String confirmPassword = userRegisterForm.getConfirmPassword();
        if (!ObjectUtils.isNull(confirmPassword) && !confirmPassword.equals(password)) {
            errors.rejectValue("confirmPassword", "confirmPassword.fail", "两次密码输入不匹配");
        }
    }
}
