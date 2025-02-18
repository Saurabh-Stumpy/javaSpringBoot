package com.example.minor_1.services;

import com.example.minor_1.models.Author;
import com.example.minor_1.models.Book;
import com.example.minor_1.models.Genre;
import com.example.minor_1.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    AuthorService authorService;

    @Autowired
    BookRepository bookRepository;

    public void createOrUpdate(Book book) {
        Author bookAuthor = book.getMy_author();

        Author savedAuthor = authorService.getOrCreate(bookAuthor);

        // Map the author to the book
        book.setMy_author(savedAuthor);

        bookRepository.save(book);
    }

    public List<Book> find(String searchKey, String searchvalue) throws Exception {
        switch (searchKey){
            case "id" :
                return bookRepository.findById(Integer.parseInt(searchvalue)).stream().toList();
            case "genre":
                return bookRepository.findByGenre(Genre.valueOf(searchvalue));
            case "author_name" :
                return bookRepository.findByAuthor_name(searchvalue);
            case "name":
                return bookRepository.findByName(searchvalue);
            default :
                throw new Exception("Search key not valid" + searchKey);
        }
    }
}
