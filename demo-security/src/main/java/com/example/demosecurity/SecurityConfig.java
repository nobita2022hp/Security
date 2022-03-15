package com.example.demosecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomerUserDetailService service;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user1")
//                .password(passwordEncoder().encode("user1pass"))
//                .authorities("USER");
        auth.userDetailsService(service).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/home").permitAll()
                .and().authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
                .and().authorizeRequests().antMatchers("/account").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .anyRequest().authenticated().and().
                formLogin().loginPage("/account/login").permitAll()
                .defaultSuccessUrl("/account/user-info")
                .failureUrl("/account/login?success=false")
                .loginProcessingUrl("/j_spring_security_check")
                .and().exceptionHandling().accessDeniedPage("/account/login?success=denied");
    }
}
