package com.example.minor_1.dtos;


import com.example.minor_1.models.SecuredUser;
import com.example.minor_1.models.Student;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStudentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @Max(50)
    private Integer age;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public Student to(){
        return Student.builder()
                .name(this.name)
                .email(this.email)
                .age(this.age)
                .securedUser(
                        SecuredUser.builder()
                                .username(this.username)
                                .password(this.password)
                                .build()
                )
                .build();
    }

}
