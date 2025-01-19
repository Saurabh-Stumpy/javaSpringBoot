package com.example.demo_redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;


@Configuration
public class RedisConfig {

    @Value("$r{redis.host.url}")
    String host;

    @Value("$r{redis.host.port}")
    Integer port;

    @Value("$r{redis.auth.password}")
    String password;

    @Bean
    public LettuceConnectionFactory getConnection(){

        //Configuration - Specify-ing the properties

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(host, port);
        configuration.setPassword(password);

        //Connection - Establishing the connection
        /* We can also pass host and port to LettuceConnectionFactory
        ,but we cannot pass the password so we created the configuration
         */
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(configuration);

        return lettuceConnectionFactory;

    }

    // Redis Template :For executing any command we need Redis Template.

}
