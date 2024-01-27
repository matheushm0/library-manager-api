package com.br.mhm.libraryapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "BORROWING_RECORD")
@Getter
@Setter
@NoArgsConstructor
public class BorrowingRecord {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties({"borrowingRecord"})
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @NotNull
    private Book book;

    @JsonIgnoreProperties({"borrowingRecord"})
    @ManyToOne
    @JoinColumn(name = "patron_id", nullable = false)
    @NotNull
    private Patron patron;

    @NotNull
    @Column(name = "borrowing_date")
    private LocalDate borrowingDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    public BorrowingRecord(Long bookId, Long patronId, LocalDate borrowingDate) {
        this.book = new Book();
        this.book.setId(bookId);
        this.patron = new Patron();
        this.patron.setId(patronId);
        this.borrowingDate = borrowingDate;
    }

    public void setBookId(Long bookId) {
        this.book = new Book();
        this.book.setId(bookId);
    }

    public void setPatronId(Long patronId) {
        this.patron = new Patron();
        this.patron.setId(patronId);
    }
}
