package com.example.bookshopsystem.service;

import com.example.bookshopsystem.model.entity.Book;

import java.io.IOException;
import java.util.List;

public interface BookService{
    void seedBook() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

}
