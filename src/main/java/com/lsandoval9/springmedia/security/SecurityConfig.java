package com.lsandoval9.springmedia.security;

import com.lsandoval9.springmedia.repositories.RoleRepository;
import com.lsandoval9.springmedia.repositories.UserRepository;
import com.lsandoval9.springmedia.security.auth.SecurityUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public SecurityConfig(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(securityUserService());

        auth.authenticationProvider(authProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .anyRequest().permitAll();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    @Bean
    public SecurityUserService securityUserService() {

        return new SecurityUserService(userRepository, passwordEncoder(), roleRepository);

    }

}
