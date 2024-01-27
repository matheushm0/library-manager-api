package com.br.mhm.libraryapi.controller;

import com.br.mhm.libraryapi.model.Book;
import com.br.mhm.libraryapi.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> saveBook(@RequestBody @Valid Book book, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errors);
        }

        return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody @Valid Book book, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errors);
        }

        if (bookService.findBookById(id).isEmpty())
            return ResponseEntity.notFound().build();

        book.setId(id);

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
