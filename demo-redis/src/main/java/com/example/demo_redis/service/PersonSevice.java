package com.example.demo_redis.service;


import com.example.demo_redis.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo_redis.repository.PersonRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonSevice {

    @Autowired
    PersonRepository personRepository;

    public void create(Person person){
        personRepository.set(person);
    }

    public Person get(String personId) {
        return personRepository.get(personId);
    }

    public List<Person> get() {
        Set<String> keys = personRepository.getAllKeys();
        return keys.stream()
                .map(k->personRepository
                        .getByKey(k))
                .collect(Collectors.toList());

    }
    //----------------List Operations-----------------

    public void lpush(Person person){
        personRepository.lpush(person);
    }

    public void rpush(Person person){
        personRepository.rpush(person);
    }

    public List<Person> lpop(int count){
        return personRepository.lpop(count);
    }

    public List<Person> rpop(int count){
        return personRepository.rpop(count);
    }

    public List<Person> lrange(int start,int end){
        return personRepository.lrange(start,end);
    }

    //------------------Hash Ops---------------------

    public void setPersonHash(Person person){
        personRepository.hmset(person);
    }

    public Person getPersonFromHash(String person){
        return personRepository.hgetall(person);
    }


}
