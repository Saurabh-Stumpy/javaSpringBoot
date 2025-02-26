package com.example.minor_1.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @CreationTimestamp
    private Date createdOn;

    @JsonIgnoreProperties({"admin"})
    @OneToMany(mappedBy = "admin")
    private List<Transaction> transactionList;


    @OneToOne
    @JsonIgnoreProperties("{admin}")
    @JoinColumn
    private SecuredUser securedUser;

}
