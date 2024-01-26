package com.br.mhm.libraryapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "BORROWING_RECORD")
@Getter
@Setter
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @NotNull
    private Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id", nullable = false)
    @NotNull
    private Patron patron;

    @NotNull
    @Column(name = "borrowing_date")
    private LocalDate borrowingDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    public void setBookId(Long bookId) {
        this.book = new Book();
        this.book.setId(bookId);
    }

    public void setPatronId(Long patronId) {
        this.patron = new Patron();
        this.patron.setId(patronId);
    }
}
