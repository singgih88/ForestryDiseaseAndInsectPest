package com.jecyhw.config.security;

import com.jecyhw.config.ajax.AjaxAwareAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by jecyhw on 16-11-9.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .headers()
                    .frameOptions().sameOrigin()
                .and()
                    .authorizeRequests()
                    .antMatchers("/plugin/**", "/css/**", "/img/**", "/js/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login?auth")
                    .loginProcessingUrl("/login")
                    .failureUrl("/login?error")
                    .defaultSuccessUrl("/")
                    .usernameParameter("userName")
                    .passwordParameter("password")
                    .permitAll()
                .and()
                    .logout().logoutSuccessUrl("/login").logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                .and().csrf()
                .and().exceptionHandling().authenticationEntryPoint(new AjaxAwareAuthenticationEntryPoint("/login"));
    }
}
