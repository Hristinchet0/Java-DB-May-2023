package com.example.bookshopsystem;

import com.example.bookshopsystem.model.entity.AgeRestriction;
import com.example.bookshopsystem.service.AuthorService;
import com.example.bookshopsystem.service.BookService;
import com.example.bookshopsystem.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;

    private final AuthorService authorService;

    private final BookService bookService;

    private final BufferedReader bufferedReader;


    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService, BufferedReader bufferedReader) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println("Select exercise:");
        int exerciseNumber = Integer.parseInt(bufferedReader.readLine());

        switch (exerciseNumber) {
            case 1:
                p01BooksTitlesByAgeRestriction();
                break;
            case 2:
                p02GoldenBooks();
                break;
            case 3:
                p03BooksByPrice();
                break;
            case 4:
                p04NotReleasedBooks();
                break;
            case 5:
                p05BooksReleasedBeforeDate();
                break;
            case 6:
                p06AuthorsSearch();
                break;
            case 7:
                p07BooksSearch();
                break;
            case 8:
                p08BookTitleSearch();
                break;
            case 9:
                p09CountBooks();
                break;
            case 10:
                p10TotalBookCopies();
                break;
            case 11:
                p11ReducedBook();
                break;
            case 12:
                p12IncreaseBookCopies();
                break;
            case 13:
                p13RemoveBooks();
                break;
        }
    }

    private void p13RemoveBooks() throws IOException {
        System.out.println("Enter number of copies:");
        int copies = Integer.parseInt(bufferedReader.readLine());

        System.out.println(bookService.deleteBooks(copies));
    }

    private void p12IncreaseBookCopies() throws IOException {
        System.out.println("Enter release date in format dd MM yyyy (example: 12 Oct 2005):");
        LocalDate dateAfter = LocalDate.parse(bufferedReader.readLine(), DateTimeFormatter.ofPattern("dd MMM yyyy"));

        System.out.println("Enter increased with:");
        int increaseCopies = Integer.parseInt(bufferedReader.readLine());

        int affectedRows = this.bookService.updateBookCopies(dateAfter, increaseCopies);
        System.out.println(increaseCopies * affectedRows);


    }

    private void p11ReducedBook() throws IOException {
        System.out.println("Enter book title:");
        String bookTitle = bufferedReader.readLine();
        bookService.findBookInfo(bookTitle)
                .forEach(System.out::println);
    }

    private void p10TotalBookCopies() {
        authorService.findAllAuthorsAndTheirTotalBooksCopies()
                .forEach(System.out::println);
    }

    private void p09CountBooks() throws IOException {
        System.out.println("Enter length of title:");
        int titleLength = Integer.parseInt(bufferedReader.readLine());

        System.out.println(bookService.findAllBooksWithLengthLongerThanGivenLength(titleLength));
    }

    private void p08BookTitleSearch() throws IOException {
        System.out.println("Enter first letters of author last name:");
        String searchedLetters = bufferedReader.readLine().toLowerCase();

        bookService.findAllBooksWhichAuthorLastNameStartWithSearchedLetters(searchedLetters)
                .forEach(System.out::println);
    }

    private void p07BooksSearch() throws IOException {
        System.out.println("Enter searched string:");
        String searchedString = bufferedReader.readLine().toLowerCase();

        bookService.findAllBooksWhichContainAGivenString(searchedString)
                .forEach(System.out::println);
    }

    private void p06AuthorsSearch() throws IOException {
        System.out.println("Enter last letter of author first name:");
        String lastLetterName = bufferedReader.readLine();

        authorService.findAllAuthorsWhoseFirstNameEndWithGivenLetter(lastLetterName)
                .forEach(System.out::println);
    }

    private void p05BooksReleasedBeforeDate() throws IOException {
        System.out.println("Enter release date in format dd-MM-yyyy:");
        LocalDate localDate = LocalDate.parse(bufferedReader.readLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        bookService.findAllBooksAfterReleaseDate(localDate)
                .forEach(System.out::println);

    }

    private void p04NotReleasedBooks() throws IOException {
        System.out.println("Enter year:");
        int year = Integer.parseInt(bufferedReader.readLine());

        bookService.findAllBooksThatAreNotInGivenReleasedDate(year)
                .forEach(System.out::println);
    }

    private void p03BooksByPrice() {
        bookService.findAllGoldenBooksByPrice()
                .forEach(System.out::println);

    }

    private void p02GoldenBooks() {
        bookService.findAllGoldenBooksWithCopiesLessThan5000()
                .forEach(System.out::println);
    }

    private void p01BooksTitlesByAgeRestriction() throws IOException {
        System.out.println("Enter age restriction:");
        AgeRestriction ageRestriction = AgeRestriction.valueOf(bufferedReader.readLine().toUpperCase());

        bookService.findAllBookTitlesWithAgeRestriction(ageRestriction)
                .forEach(System.out::println);
    }


    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBook();
    }
}
