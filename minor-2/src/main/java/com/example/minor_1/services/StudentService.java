package com.example.minor_1.services;

import com.example.minor_1.models.SecuredUser;
import com.example.minor_1.models.Student;
import com.example.minor_1.repositories.StudentCacheRepository;
import com.example.minor_1.repositories.StudentRepository;
import com.example.minor_1.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UserService userService;

    @Autowired
    StudentCacheRepository studentCacheRepository;


    public void create(Student student) {
        SecuredUser securedUser = student.getSecuredUser();
        securedUser = userService.save(securedUser, Constants.STUDENT_USER);

        student.setSecuredUser(securedUser);

        studentRepository.save(student);
    }

    public Student find(int studentId) {

        Student student = studentCacheRepository.get(studentId);
        if(student != null){
            return student;
        }
            student = studentRepository.findById(studentId).orElse(null);
        if (student != null){
            studentCacheRepository.set(student);
        }

        return student;
    }
}
