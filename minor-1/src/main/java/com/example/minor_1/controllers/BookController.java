package com.example.minor_1.controllers;

import com.example.minor_1.dtos.CreateBookRequest;
import com.example.minor_1.models.Book;
import com.example.minor_1.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/book")
    public void createBook(@RequestBody @Valid CreateBookRequest createBookRequest){
        bookService.createOrUpdate(createBookRequest.to());
    }

    @GetMapping("/book")
    public List<Book> getBooks(@RequestParam("key") String key,
                               @RequestParam("value") String value) throws Exception {
        //localhost:8080/book?key=author_name&value=Peter
        //localhost:8080/book?key=genre&value=FRICTIONAL

        return bookService.find(key,value);

    }
}
