package com.example.minor_1.models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true,  nullable = false)
    private String email; // email is unique identity

    @CreationTimestamp
    private Date createdOn;

    @OneToMany(mappedBy = "my_author")
    //We don't need Foreign key column here so didn't add JoinColumn
    private List<Book> bookList;

}
