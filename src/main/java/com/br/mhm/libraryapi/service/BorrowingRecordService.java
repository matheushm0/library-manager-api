package com.br.mhm.libraryapi.service;

import com.br.mhm.libraryapi.model.BorrowingRecord;
import com.br.mhm.libraryapi.repository.BorrowingRecordRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingRecordService {

    private static final Logger logger = LoggerFactory.getLogger(BorrowingRecordService.class);

    private final BorrowingRecordRepository borrowingRecordRepository;

    public BorrowingRecordService(BorrowingRecordRepository borrowingRecordRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
    }

    public Optional<BorrowingRecord> findByBookIdAndPatronIdAndReturnDateIsNull(Long bookId, Long patronId) {
        return borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);
    }

    @Transactional
    public BorrowingRecord borrowABook(Long bookId, Long patronId) {
        BorrowingRecord borrowingRecord = new BorrowingRecord();

        borrowingRecord.setBookId(bookId);
        borrowingRecord.setPatronId(patronId);
        borrowingRecord.setBorrowingDate(LocalDate.now());

        logger.info("Create borrowing record for book: {} and patron: {}", bookId, patronId);

        return borrowingRecordRepository.save(borrowingRecord);
    }

    @Transactional
    public BorrowingRecord returnABook(BorrowingRecord borrowingRecord) {
        borrowingRecord.setReturnDate(LocalDate.now());

        logger.info("Patron {} returned book {}", borrowingRecord.getPatron().getId(), borrowingRecord.getBook().getId());

        return borrowingRecordRepository.save(borrowingRecord);
    }

}
