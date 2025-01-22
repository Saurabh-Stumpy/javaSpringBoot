package com.example.demo_redis.repository;

import com.example.demo_redis.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class PersonRepository {

    @Autowired
    RedisTemplate<String,Person> redisTemplate;

    private static final Integer PERSON_VALUE_EXPIRY = 1;

    private static String PERSON_KEY_PREFIX = "Person::";

    public void set(Person person){
        this.redisTemplate.opsForValue().set(getKey(person.getId()), person,PERSON_VALUE_EXPIRY, TimeUnit.DAYS);
    }

    public Set<String> getAllKeys(){
        return this.redisTemplate.keys(PERSON_KEY_PREFIX+"*");
    }

    public Person get(String personId){
        return this.redisTemplate.opsForValue().get(getKey(personId));
    }

    public Person getByKey(String key){
        return this.redisTemplate.opsForValue().get(key);
    }

    private String getKey(String personId){
        return PERSON_KEY_PREFIX+personId;
    }

}
