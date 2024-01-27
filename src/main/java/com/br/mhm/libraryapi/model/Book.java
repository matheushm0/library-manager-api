package com.br.mhm.libraryapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "BOOK")
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 255,message = "Title must be between 1 and 255 characters")
    @Column(name = "title")
    private String title;

    @NotNull(message = "Author cannot be null")
    @Size(min = 1, max = 255, message = "Author must be between 1 and 255 characters")
    @Column(name = "author")
    private String author;

    @NotNull(message = "Publication year cannot be null")
    @Column(name = "publication_year")
    private Integer publicationYear;

    @NotNull(message = "ISBN cannot be null")
    @Size(min = 1, max = 13, message = "ISBN must be between 1 and 13 characters")
    @Column(name = "isbn")
    private String isbn;

    @JsonIgnoreProperties({"book"})
    @OneToMany(mappedBy = "book")
    private List<BorrowingRecord> borrowingRecord;

    public Book(Long id, String title, String author, Integer publicationYear, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
    }
}
