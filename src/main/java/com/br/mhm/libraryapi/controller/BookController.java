package com.br.mhm.libraryapi.controller;

import com.br.mhm.libraryapi.model.Book;
import com.br.mhm.libraryapi.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id) {
        Book book = bookService.findBookById(id).orElse(null);

        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody @Valid Book book) {
        return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody @Valid Book book) {
        if (bookService.findBookById(id).isEmpty())
            return ResponseEntity.notFound().build();

        Book bookUpdated = bookService.updateBook(book);

        return ResponseEntity.ok(bookUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        if (bookService.findBookById(id).isEmpty())
            return ResponseEntity.notFound().build();

        bookService.deleteBookById(id);

        return ResponseEntity.noContent().build();
    }
}
