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
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)         //  JAKARTA
    private Integer id;

    private String name;

    @Enumerated(value = EnumType.ORDINAL)  // JAKARTA
    private Genre genre;
    // Type not Recognised by DB -> By default it is ORDINAL
    // There are 2 types ORDINAL and String
    // ORDINAL is for storing it as number 1,2,3.....
    // String is just to store the Enum value as String

    @CreationTimestamp      // Get timestamp of record creation (Hibernate)
    private Date createdOn;

    @UpdateTimestamp        // Get the timestamp of record update (Hibernate)
    private Date updatedOn;

    @ManyToOne
    // Many books written by one author
    //Book <-> Author
    @JoinColumn
    // To create Foreign key column in Book table
    private Author my_author;

    @ManyToOne
    @JoinColumn
    private Student student;

    @OneToMany(mappedBy = "book")
    private List<Transaction> transactionList;

}
