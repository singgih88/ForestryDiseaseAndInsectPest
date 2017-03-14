package com.jecyhw.mvc.service.impl;

import com.jecyhw.core.util.ObjectUtils;
import com.jecyhw.mvc.domain.User;
import com.jecyhw.mvc.repository.UserRepository;
import com.jecyhw.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jecyhw on 16-11-9.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (!ObjectUtils.isNull(user)) {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//            grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.roleById(user.getRoleId()).getName()));
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), grantedAuthorities);
        }
        throw new UsernameNotFoundException(String.format("用户 '%s' 没有找到", username));
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User queryByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
