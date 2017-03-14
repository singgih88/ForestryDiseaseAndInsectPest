package com.jecyhw.mvc.controller;

import com.jecyhw.core.response.Response;
import com.jecyhw.core.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by jecyhw on 16-11-22.
 */

@RestController
@RequestMapping(value = "user")
public class UserController {

    @RequestMapping(value = "changePassword", method = RequestMethod.GET)
    public ModelAndView changePasswordPage() {
        return new ModelAndView("user/changePassword");
    }

    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    public Response<?> changePassword() {
        return Response.success(ObjectUtils.nullObj());
    }
}
