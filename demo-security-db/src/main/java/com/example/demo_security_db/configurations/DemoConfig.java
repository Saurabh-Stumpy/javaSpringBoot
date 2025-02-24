package com.example.demo_security_db.configurations;

import com.example.demo_security_db.services.DemoSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoConfig {

    @Autowired DemoSecurityService demoSecurityService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authz ->
                        authz
                                .requestMatchers("/doctor/**").hasAuthority("DOCTOR_DETAILS_AUTHORITY")
                                .requestMatchers("/deo/**").hasAuthority("DEO_DETAILS_AUTHORITY")
                                .requestMatchers("/ceo/**").hasAuthority("CEO_DETAILS_AUTHORITY")
                                .requestMatchers("/schedule/**").hasAnyAuthority("SCHEDULE_APPOINTMENTS")
                                .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults()).rememberMe(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new DemoSecurityService();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
