package com.example.bookshopsystem.service;

import com.example.bookshopsystem.model.entity.AgeRestriction;
import com.example.bookshopsystem.model.entity.Book;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService{
    void seedBook() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllBookTitlesWithAgeRestriction(AgeRestriction ageRestriction);

    List<String> findAllGoldenBooksWithCopiesLessThan5000();

    List<String> findAllGoldenBooksByPrice();

    List<String> findAllBooksThatAreNotInGivenReleasedDate(int year);

    List<String> findAllBooksAfterReleaseDate(LocalDate localDate);

    List<String> findAllBooksWhichContainAGivenString(String searchedString);

    List<String> findAllBooksWhichAuthorLastNameStartWithSearchedLetters(String searchedLetters);

    int findAllBooksWithLengthLongerThanGivenLength(int titleLength);

    List<String> findAllBooksCopiesByAuthor();

    List<String> findBookInfo(String bookTitle);


    @Transactional
    int updateBookCopies(LocalDate dateAfter, int increaseCopies);

    @Transactional
    int deleteBooks(int copies);
}
