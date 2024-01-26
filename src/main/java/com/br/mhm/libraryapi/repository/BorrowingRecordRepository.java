package com.br.mhm.libraryapi.repository;

import com.br.mhm.libraryapi.model.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    @Query("SELECT br FROM BorrowingRecord br WHERE br.book.id = ?1 AND br.patron.id = ?2")
    public Optional<BorrowingRecord> findByBookIdAndPatronId(Long bookId, Long patronId);

}
