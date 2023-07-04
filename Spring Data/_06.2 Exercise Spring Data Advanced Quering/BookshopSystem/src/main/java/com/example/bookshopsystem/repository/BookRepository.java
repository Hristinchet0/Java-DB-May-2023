package com.example.bookshopsystem.repository;

import com.example.bookshopsystem.model.entity.AgeRestriction;
import com.example.bookshopsystem.model.entity.Book;
import com.example.bookshopsystem.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDate);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);

    List<Book> findAllByPriceIsLessThanOrPriceIsGreaterThan(BigDecimal price, BigDecimal price2);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate lower, LocalDate upper);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDate);

    List<Book> findAllByTitleContaining(String searched);

    List<Book> findAllByAuthor_LastNameStartsWith(String searched);

    @Query("SELECT count(b) FROM Book b WHERE LENGTH(b.title) > :param")
    int countOfBooksWithTitleLengthMoreThan(@Param(value = "param") int titleLength);

    List<Book> findAllByTitle(String title);


    @Query("UPDATE Book b SET b.copies = b.copies +:addCopies WHERE b.releaseDate >:afterDate")
    @Modifying
    int updateBookCopies(LocalDate afterDate, int addCopies);

    @Modifying
    int deleteByCopiesLessThan(int copies);
}
