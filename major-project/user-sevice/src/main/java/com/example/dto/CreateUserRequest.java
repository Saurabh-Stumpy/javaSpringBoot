package com.example.dto;

import com.example.models.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String phone;

    private String email;

    @Min(18)
    private int age;

    public User to(){
        return User.builder()
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .age(this.age)
                .build();
    }

}
