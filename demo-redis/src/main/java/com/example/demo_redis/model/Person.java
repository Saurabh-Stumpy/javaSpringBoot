package com.example.demo_redis.model;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person implements Serializable {

    // Serializable means the class must be able to convert to byte stream
    // Integer, Double, String all are Serializable

    private String id;

    private String name;

    private String age;

    private Address address;
}
