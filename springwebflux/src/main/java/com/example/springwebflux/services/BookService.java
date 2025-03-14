package com.example.springwebflux.services;

import com.example.springwebflux.CustomExceptions.ResourceNotFoundException;
import com.example.springwebflux.Repository.BookRepository;
import com.example.springwebflux.models.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    public Mono<Book> getBookById(Long id) {
        //return bookRepository.findById(id).switchIfEmpty(Mono.error(new ResourceNotFoundException("Book Not Found for ")));

        return bookRepository.findById(id).switchIfEmpty(Mono.error(new ResourceNotFoundException("Book Not Found for " + id)));

        //return Mono.error(new ResourceNotFoundException("Book Not Found for " + id));
    }

    public Mono<Book> createBook(String title, String author) {
        return bookRepository.save(new Book(null, title, author));
    }

    @Transactional
    public Mono<Boolean> deleteBookById(Long id) {
        return bookRepository.existsById(id)
                .flatMap(exist -> {
                    if (exist) {
                        return bookRepository.deleteById(id).thenReturn(true);
                    } else {
                        return Mono.just(false);
                    }
                });
    }

    public Flux<Book> testBooks() {
        return bookRepository.findAll();
    }
}
