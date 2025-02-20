package com.example.demo_security.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
public class DemoConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .authorities("deo")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
