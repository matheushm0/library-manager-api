package com.br.mhm.libraryapi.service;

import com.br.mhm.libraryapi.model.BorrowingRecord;
import com.br.mhm.libraryapi.repository.BorrowingRecordRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;

    public BorrowingRecordService(BorrowingRecordRepository borrowingRecordRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
    }

    public Optional<BorrowingRecord> findByBookIdAndPatronId(Long bookId, Long patronId) {
        return borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId);
    }

    @Transactional
    public BorrowingRecord borrowABook(Long bookId, Long patronId) {
        BorrowingRecord borrowingRecord = new BorrowingRecord();

        borrowingRecord.setBookId(bookId);
        borrowingRecord.setPatronId(patronId);
        borrowingRecord.setBorrowingDate(LocalDate.now());

        return borrowingRecordRepository.save(borrowingRecord);
    }

    @Transactional
    public BorrowingRecord returnABook(BorrowingRecord borrowingRecord) {

        borrowingRecord.setReturnDate(LocalDate.now());

        return borrowingRecordRepository.save(borrowingRecord);
    }

}
