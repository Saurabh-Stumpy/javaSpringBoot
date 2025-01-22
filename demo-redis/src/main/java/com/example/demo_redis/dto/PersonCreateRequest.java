package com.example.demo_redis.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import com.example.demo_redis.model.Address;
import com.example.demo_redis.model.Person;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonCreateRequest {

    @NotBlank
    private String name;

    private String age;

    private Address address;

    public Person to(){
        return Person.builder()
                .name(this.name)
                .age(this.age)
                .address(this.address)
                .id(UUID.randomUUID().toString())
                .build();
    }

}
