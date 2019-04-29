package ru.kazimir.bortnik.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.kazimir.bortnik.config.handler.AppUrlAuthenticationSuccessHandler;


@Configuration
@Order(2)
public class WebSpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public WebSpringSecurityConfig(@Qualifier("appUserDetailsService") UserDetailsService userDetailsService, AccessDeniedHandler accessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/", "/home", "/webjars/**", "/css/**").permitAll()
                .antMatchers("/items/**").hasAuthority("CUSTOMER")
                .antMatchers("/users/**").hasAuthority("ADMINISTRATOR")
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/login")
                .successHandler(authenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                .csrf().disable();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AppUrlAuthenticationSuccessHandler();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

}
