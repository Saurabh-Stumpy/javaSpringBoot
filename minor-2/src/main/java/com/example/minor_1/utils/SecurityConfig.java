package com.example.minor_1.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf-> csrf.disable()).authorizeHttpRequests(authz ->
                        authz
                                .requestMatchers(HttpMethod.GET,"/student/**").hasAuthority(Constants.STUDENT_SELF_INFO_AUTHORITY)
                                .requestMatchers(HttpMethod.GET,"/student-by-id/**").hasAuthority(Constants.STUDENT_INFO_AUTHORITY)
                                .requestMatchers(HttpMethod.POST,"/admin/**").hasAuthority(Constants.CREATE_ADMIN_AUTHORITY)
                                .requestMatchers(HttpMethod.GET,"/book/**").hasAuthority(Constants.READ_BOOK_AUTHORITY)
                                .requestMatchers(HttpMethod.POST,"/book/**").hasAuthority(Constants.CREATE_BOOK_AUTHORITY)
                                .requestMatchers("/transaction/payment/**").hasAuthority(Constants.MAKE_PAYMENT_AUTHORITY)
                                .requestMatchers("transaction/**").hasAuthority(Constants.INITIATE_TRANSACTION_AUTHORITY)
                                .requestMatchers("/**").permitAll())
                .formLogin(Customizer.withDefaults()).rememberMe(Customizer.withDefaults());
        return http.build();
    }



}

