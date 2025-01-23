package com.example.demo_redis.controller;


import com.example.demo_redis.dto.PersonCreateRequest;
import jakarta.validation.Valid;
import com.example.demo_redis.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo_redis.service.PersonSevice;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    /*
    String Operations
     */

    @Autowired
    PersonSevice personSevice;

    @PostMapping("/")
    public void createPerson(@RequestBody @Valid PersonCreateRequest request){
        personSevice.create(request.to());
    }

    @GetMapping("/")
    public Person getPerson(@RequestParam("id") String personId){
        return personSevice.get(personId);
    }

    @GetMapping("/all")
    public List<Person> getPeople(){
        return personSevice.get();
    }


    /*
     List Operations
     */
    @PostMapping("/lpush")
    public void lpush(@RequestBody @Valid PersonCreateRequest request){
        personSevice.lpush(request.to());
    }

    @PostMapping("/rpush")
    public void rpush(@RequestBody @Valid PersonCreateRequest request){
        personSevice.rpush(request.to());
    }

    @DeleteMapping("/lpop")
    public List<Person> lpop(@RequestParam(value = "count", required = false, defaultValue = "1") int count){
        return personSevice.lpop(count);
    }

    @DeleteMapping("/rpop")
    public List<Person> rpop(@RequestParam(value = "count", required = false, defaultValue = "1") int count){
        return personSevice.rpop(count);
    }

    @GetMapping("/lrange")
    public List<Person> lrange(@RequestParam(value = "start", required = false,defaultValue = "0") int start,
                               @RequestParam(value = "end", required = false,  defaultValue ="-1") int end){
        return personSevice.lrange(start,end);
    }

    /*
     Hash Operations
     */

    @PostMapping("/hash")
    public void createPersonInHash(@RequestBody @Valid PersonCreateRequest request){
        personSevice.setPersonHash(request.to());
    }

    @GetMapping("/hash/{personId}")
    public Person getPersonFromHash(@PathVariable("personId") String personId){
        return personSevice.getPersonFromHash(personId);
    }


}
