package com.jecyhw.mvc.service;

import com.jecyhw.mvc.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by jecyhw on 16-11-9.
 */
public interface UserService extends UserDetailsService {
    void save(User user);
    User queryByUserName(String userName);
}
