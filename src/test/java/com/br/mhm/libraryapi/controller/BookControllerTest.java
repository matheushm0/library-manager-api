package com.br.mhm.libraryapi.controller;

import com.br.mhm.libraryapi.model.Book;
import com.br.mhm.libraryapi.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    private final MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    Book BOOK_1 = new Book(1L, "Amazing Spider-Man Omnibus, Vol. 1", "Stan Lee", 2007, "0785124020");
    Book BOOK_2 = new Book(2L, "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", 1986, "0425098389");

    public BookControllerTest(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void findAllBooks_shouldReturnListOfBooks() throws Exception {
        List<Book> books = Arrays.asList(BOOK_1, BOOK_2);
        when(bookService.findAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void findBookById_shouldReturnBookWhenExists() throws Exception {
        Long bookId = 1L;
        when(bookService.findBookById(bookId)).thenReturn(Optional.of(BOOK_1));

        mockMvc.perform(get("/api/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void findBookById_shouldReturnNotFoundWhenDoesNotExist() throws Exception {
        Long bookId = 1L;
        when(bookService.findBookById(bookId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void saveBook_shouldReturnCreated() throws Exception {
        when(bookService.saveBook(any(Book.class))).thenReturn(BOOK_1);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Amazing Spider-Man Omnibus, Vol. 1\",\"author\":\"Stan Lee\",\"publicationYear\":2007,\"isbn\":\"0785124020\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void updateBook_shouldReturnOkWhenExists() throws Exception {
        Long bookId = 1L;
        Book updatedBook = new Book(bookId, "Updated Title", "Updated Author", 2024, "UpdatedISBN");

        when(bookService.findBookById(bookId)).thenReturn(Optional.of(BOOK_1));
        when(bookService.updateBook(any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/api/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Title\",\"author\":\"Updated Author\",\"publicationYear\":2024,\"isbn\":\"UpdatedISBN\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.author").value("Updated Author"))
                .andExpect(jsonPath("$.publicationYear").value(2024))
                .andExpect(jsonPath("$.isbn").value("UpdatedISBN"));
    }

    @Test
    void updateBook_shouldReturnNotFoundWhenDoesNotExist() throws Exception {
        Long bookId = 1L;
        when(bookService.findBookById(bookId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Title\",\"author\":\"Updated Author\",\"publicationYear\":2022,\"isbn\":\"UpdatedISBN\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBookById_shouldReturnNoContentWhenExists() throws Exception {
        Long bookId = 1L;
        when(bookService.findBookById(bookId)).thenReturn(Optional.of(BOOK_1));

        mockMvc.perform(delete("/api/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBookById_shouldReturnNotFoundWhenDoesNotExist() throws Exception {
        Long bookId = 1L;
        when(bookService.findBookById(bookId)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
