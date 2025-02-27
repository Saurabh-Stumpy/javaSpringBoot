package com.example.minor_1.dtos;


import com.example.minor_1.models.Admin;
import com.example.minor_1.models.SecuredUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAdminRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;


    public Admin to(){
        return Admin.builder()
                .name(this.name)
                .email(this.email)
                .securedUser(
                        SecuredUser.builder()
                                .username(this.username)
                                .password(this.password)
                                .build()
                )
                .build();
    }
}
