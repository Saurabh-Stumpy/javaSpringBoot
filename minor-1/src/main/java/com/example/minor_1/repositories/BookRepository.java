package com.example.minor_1.repositories;

import com.example.minor_1.models.Book;
import com.example.minor_1.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {

    List<Book> findByGenre(Genre genre);

    @Query("select b from Book b, Author a where b.my_author.id=a.id and a.name = ?1")
    List<Book> findByAuthor_name(String name);

    List<Book> findByName(String bookName);
}
