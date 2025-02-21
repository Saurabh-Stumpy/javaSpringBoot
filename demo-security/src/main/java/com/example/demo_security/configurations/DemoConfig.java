package com.example.demo_security.configurations;

import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class DemoConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails karan = User.builder()
                .username("Karan")
                .password("$2a$10$AqcpKsNYDEjOLekbiQDzMOSvOTLXbO3pFAdCpKracdPR4ac3LNmTC")
                .authorities("deo")
//                .roles("deo")
                .build();
        UserDetails pankaj = User.builder()
                .username("Pankaj")
                .password("$2a$10$t8H2yoxSTGUYfDQN3ySLpOcSrkf/i/b/ofHoLd0x6i1JX/zYaTonq")
                .authorities("doctor")
//                .roles("doctor")
                .build();
        UserDetails nitish = User.builder()
                .username("Nitish")
                .password("$2a$10$.3Es3eHMzYRISswHO7yzeuSAG5DZ0Ox2dAethTsKnTCBdfWMcpwDO")
                .authorities("ceo")
//                .roles("doctor")
                .build();

        return new InMemoryUserDetailsManager(karan,pankaj,nitish);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authz ->
                authz
                        .requestMatchers("/doctor/**").hasAuthority("doctor")
                        .requestMatchers("/deo/**").hasAuthority("deo")
                        .requestMatchers("/ceo/**").hasAuthority("ceo")
                        .requestMatchers("/schedule/**").hasAnyAuthority("deo","doctor")
                        .anyRequest().permitAll())
                        .formLogin(Customizer.withDefaults()).rememberMe(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder getPE(){
        return new BCryptPasswordEncoder();
    }

}
