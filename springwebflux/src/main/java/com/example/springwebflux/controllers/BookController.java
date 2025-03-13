package com.example.springwebflux.controllers;

import com.example.springwebflux.dto.testBookDto;
import com.example.springwebflux.models.Book;
import com.example.springwebflux.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;


    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @QueryMapping
    public Flux<Book> getBooks() {
        return bookService.getAllBooks();
    }

    @QueryMapping
    public Mono<Book> getBookById(@Argument Long id) {
        return bookService.getBookById(id);
    }

    @MutationMapping
    public Mono<Book> createBook(@Argument String title, @Argument String author) {
        return bookService.createBook(title, author);
    }

    @MutationMapping
    public Mono<Boolean> deleteBookById(@Argument Long id) {
        return bookService.deleteBookById(id);
    }

    @QueryMapping
    public Mono<List<Book>> testBooks() {
        /*return bookService.testBooks()
                .flatMap(books -> Mono.just(books.getTitle()))
                .collectList();*/

        return bookService.testBooks()
                .doOnNext(book -> System.out.println("Fetched Book : " + book.getTitle()))
                .map(book -> new Book(book.getTitle(), null))
                .collectList();
    }
}
