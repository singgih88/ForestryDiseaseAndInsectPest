package com.jecyhw.mvc.controller;

import com.jecyhw.config.App;
import com.jecyhw.config.deepzoom.DeepZoomFileProperties;
import com.jecyhw.config.external.LinkConfig;
import com.jecyhw.config.external.LinkProperties;
import com.jecyhw.core.util.ObjectUtils;
import com.jecyhw.mvc.domain.*;
import com.jecyhw.mvc.domain.form.UserRegisterForm;
import com.jecyhw.mvc.domain.form.UserRegisterFormValidator;
import com.jecyhw.mvc.service.UserService;
import com.jecyhw.mvc.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jecyhw on 16-10-31.
 */
@RestController
public class HomeController {

    @Autowired
    LinkConfig linkConfig;

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRegisterFormValidator userRegisterFormValidator;

    @Autowired
    App app;

    @Autowired
    BaseService<Disease> diseaseService;

    @Autowired
    BaseService<Pest> pestService;

    @RequestMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public ModelAndView registerPage() {
        Map<String, Object> map = new HashMap<>();
        map.put("user", new UserRegisterForm());
        map.put("app", app);
        return new ModelAndView("register", map);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("user") UserRegisterForm simpleUser, BindingResult bindingResult) {
        userRegisterFormValidator.validate(simpleUser, bindingResult);

        if (!bindingResult.hasErrors() && !ObjectUtils.isNull(userService.queryByUserName(simpleUser.getUserName()))) {
            bindingResult.rejectValue("userName", "userName.exists", "用户名已经存在");
        }
        if (bindingResult.hasErrors()) {
            Map<String, Object> map = bindingResult.getModel();
            map.put("app", app);
            return new ModelAndView("register", map);
        }

        User newUser = new User();
        newUser.setUserName(simpleUser.getUserName());
        newUser.setPassword(bCryptPasswordEncoder.encode(simpleUser.getPassword()));
        newUser.setRegisterDate(new Date());

        userService.save(newUser);

        Map<String, Object> map = new HashMap<>();
        map.put("user", new UserRegisterForm());
        map.put("app", app);
        return new ModelAndView(new RedirectView("login"), map);
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView loginPage() {
        Map<String, Object> map = new HashMap<>();
        map.put("user", new UserRegisterForm());
        map.put("app", app);
        return new ModelAndView("login", map);
    }

    @RequestMapping(value = "about", method = RequestMethod.GET)
    public ModelAndView about() {
        return new ModelAndView("about", "app", app);
    }

    @RequestMapping(value = "link/{type}", method = RequestMethod.GET)
    public ModelAndView plant(@PathVariable String type) {
        return new ModelAndView("link", "link", new ExternalLink(type, linkConfig.getLink(type)));
    }

    @RequestMapping(value = "intro", method = RequestMethod.GET)
    public ModelAndView intro() {
        Map<String, List<Picture>> map = new HashMap<>();
        List<Disease> diseases = diseaseService.findBySource(app.getName());
        for (Disease disease :diseases) {
            map.put(disease.getChineseName(), disease.getPictures());
        }

        List<Pest> pests = pestService.findBySource(app.getName());
        for (Pest pest : pests) {
            map.put(pest.getChineseName(), pest.getPictures());
        }
        return new ModelAndView(app.getName(), "map", map);
    }
}
