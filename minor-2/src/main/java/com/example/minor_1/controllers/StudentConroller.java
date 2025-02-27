package com.example.minor_1.controllers;


import com.example.minor_1.dtos.CreateStudentRequest;
import com.example.minor_1.models.SecuredUser;
import com.example.minor_1.models.Student;
import com.example.minor_1.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentConroller {

    @Autowired
    StudentService studentService;

    // UnSecured
    @PostMapping("/student")
    public void createStudent(@RequestBody @Valid CreateStudentRequest studentRequest){
        studentService.create(studentRequest.to());
    }

    //Secured
    // Only for admins to that they can see any student details
    @GetMapping("/student-by-id/{id}")
    public Student findStudentById(@PathVariable("id") int studentId){
        return studentService.find(studentId);
    }


// Only for student so that they can see their own details
    @GetMapping("/student")
    public Student findStudent(){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        SecuredUser user = (SecuredUser) authentication.getPrincipal();
        int studentId = user.getStudent().getId();

        return studentService.find(studentId);
    }

}
