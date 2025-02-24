package com.example.demo_security_db.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/hello")
    public String greet(){
        return "Hello World!";
    }

    // doctor
    @GetMapping("/doctor/details")
    public String doctorAPI(){
        return "Hello Doctor";
    }

    // deo
    @GetMapping("/deo/details")
    public String deoAPI(){
        return "Hello data entry operator";
    }

    //ceo
    @GetMapping("/ceo/details")
    public String ceoAPI(){
        return "Hello ceo";
    }

    // eiter doctor or deo
    @GetMapping("/schedule/appointments")
    public String scheduleAppointment(){
        return "Hello! your appointment is scheduled!";
    }

}