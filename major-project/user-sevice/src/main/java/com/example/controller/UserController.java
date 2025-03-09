package com.example.controller;

import com.example.dto.CreateUserRequest;
import com.example.dto.GetUserResponse;
import com.example.models.User;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public void createUser(@RequestBody @Valid CreateUserRequest userRequest) throws JsonProcessingException {
        userService.create(userRequest.to());
    }

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable("userId") int userId) throws Exception {
        return userService.get(userId);
    }
}
