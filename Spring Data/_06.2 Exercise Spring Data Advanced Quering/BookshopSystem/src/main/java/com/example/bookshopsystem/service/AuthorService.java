package com.example.bookshopsystem.service;

import com.example.bookshopsystem.model.entity.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsOrderByCountOfTheirBooks();

    List<String> findAllAuthorsWhoseFirstNameEndWithGivenLetter(String lastLetterName);

    List<String> findAllAuthorsAndTheirTotalBooksCopies();
}
