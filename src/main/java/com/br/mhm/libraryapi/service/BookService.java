package com.br.mhm.libraryapi.service;

import com.br.mhm.libraryapi.model.Book;
import com.br.mhm.libraryapi.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable("books")
    public List<Book> findAllBooks() {
        logger.info("Find all books");

        return bookRepository.findAll();
    }

    @Cacheable("book")
    public Optional<Book> findBookById(Long id) {
        logger.info("Find book by id: {}", id);

        return bookRepository.findById(id);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "books", allEntries = true),
            @CacheEvict(cacheNames = "book", allEntries = true)
    })
    public Book saveBook(Book book) {
        logger.info("Save book: {}", book.getTitle());

        return bookRepository.save(book);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "books", allEntries = true),
            @CacheEvict(cacheNames = "book", allEntries = true)
    })
    public Book updateBook(Book book) {
        logger.info("Update book: {}", book.getId());

        return bookRepository.save(book);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "books", allEntries = true),
            @CacheEvict(cacheNames = "book", allEntries = true)
    })
    public void deleteBookById(Long id) {
        logger.info("Delete book by id: {}", id);

        bookRepository.deleteById(id);
    }

}
