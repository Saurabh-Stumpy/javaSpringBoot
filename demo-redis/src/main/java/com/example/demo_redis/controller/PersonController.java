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

    /*
     Hash Operations
     */
}
