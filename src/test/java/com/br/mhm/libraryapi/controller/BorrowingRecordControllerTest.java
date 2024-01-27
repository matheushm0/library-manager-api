package com.br.mhm.libraryapi.controller;

import com.br.mhm.libraryapi.model.BorrowingRecord;
import com.br.mhm.libraryapi.service.BorrowingRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BorrowingRecordController.class)
public class BorrowingRecordControllerTest {
    private final MockMvc mockMvc;

    @MockBean
    private BorrowingRecordService borrowingRecordService;

    public BorrowingRecordControllerTest(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void borrowBook_shouldReturnBorrowingRecord() throws Exception {
        Long bookId = 1L;
        Long patronId = 1L;

        BorrowingRecord borrowingRecord = new BorrowingRecord(bookId, patronId, LocalDate.now());

        when(borrowingRecordService.borrowABook(bookId, patronId)).thenReturn(borrowingRecord);

        mockMvc.perform(post("/api/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.book.id").value(bookId))
                .andExpect(jsonPath("$.patron.id").value(patronId))
                .andExpect(jsonPath("$.borrowingDate").exists());
    }

    @Test
    void returnBook_shouldReturnBorrowingRecordWhenExists() throws Exception {
        Long bookId = 1L;
        Long patronId = 1L;

        BorrowingRecord borrowingRecord = new BorrowingRecord(bookId, patronId, LocalDate.now());

        when(borrowingRecordService.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)).thenReturn(Optional.of(borrowingRecord));
        when(borrowingRecordService.returnABook(borrowingRecord)).thenAnswer(invocation -> {
            BorrowingRecord argument = invocation.getArgument(0);

            argument.setReturnDate(LocalDate.now());

            return argument;
        });

        mockMvc.perform(put("/api/return/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.book.id").value(bookId))
                .andExpect(jsonPath("$.patron.id").value(patronId))
                .andExpect(jsonPath("$.borrowingDate").exists())
                .andExpect(jsonPath("$.returnDate").exists());
    }

    @Test
    void returnBook_shouldReturnNotFoundWhenDoesNotExist() throws Exception {
        Long bookId = 1L;
        Long patronId = 1L;

        when(borrowingRecordService.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/return/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
