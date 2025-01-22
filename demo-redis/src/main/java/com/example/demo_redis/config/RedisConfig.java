package com.example.demo_redis.config;

import com.example.demo_redis.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {

    @Value("${redis.host.url}")
    String host;

    @Value("${redis.host.port}")
    Integer port;

    @Value("${redis.auth.password}")
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
    /*
    *   RedisTemplate<K,V>
    *   K -> DataType of Key
    *   V -> DataType of the value
    *
    *   In RedisTemplate there are multiple operations :
    *   valueOps, listOps, setOps, etc.
    *
    * */


    @Bean
    public RedisTemplate<String, Person> getTemplate(){
        RedisTemplate<String,Person> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(getConnection());

        // To convert Java Object to String to store it on server
        // StringRedisSerializer - converts the Java String to Redis String
        // converts to bytes and Deserialize converts bytes to Java String
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // JdkSerializationRedisSerializer - Take JAVA Object and converts to Byte
        // And takes Bytes and converts to JAVA object
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());


        return  redisTemplate;
    }




}
