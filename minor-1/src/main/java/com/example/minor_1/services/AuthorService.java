package com.example.minor_1.services;

import com.example.minor_1.models.Author;
import com.example.minor_1.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public Author getOrCreate(Author author){
        Author authorRetriverd = authorRepository.findByEmail(author.getEmail());

        if(authorRetriverd == null) {
            authorRetriverd = authorRepository.save(author);
        }

        return authorRetriverd;
    }
}
