package com.br.mhm.libraryapi.controller;

import com.br.mhm.libraryapi.model.BorrowingRecord;
import com.br.mhm.libraryapi.service.BorrowingRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingRecordService;

    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        return ResponseEntity.ok(borrowingRecordService.borrowABook(bookId, patronId));
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecord borrowingRecord = borrowingRecordService.findByBookIdAndPatronId(bookId, patronId).orElse(null);

        if (borrowingRecord == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(borrowingRecordService.returnABook(borrowingRecord));
    }
}
