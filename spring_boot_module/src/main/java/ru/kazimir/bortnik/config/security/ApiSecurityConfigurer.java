package ru.kazimir.bortnik.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@Order(1)
public class ApiSecurityConfigurer extends WebSecurityConfigurerAdapter {
    private final AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public ApiSecurityConfigurer(AccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.antMatcher("/api/**")
                .authorizeRequests()
                .anyRequest()
                .hasAuthority("ADMINISTRATOR")
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                .csrf()
                .disable();
    }

}
