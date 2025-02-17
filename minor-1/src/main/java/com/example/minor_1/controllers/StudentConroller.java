package com.example.minor_1.controllers;


import com.example.minor_1.dtos.CreateStudentRequest;
import com.example.minor_1.models.Student;
import com.example.minor_1.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentConroller {

    @Autowired
    StudentService studentService;

    @PostMapping("/student")
    public void createStudent(@RequestBody @Valid CreateStudentRequest studentRequest){
        studentService.create(studentRequest.to());
    }

    @GetMapping("/student")
    public Student findStudent(@RequestParam("id") int studentId){
        return studentService.find(studentId);
    }

}
