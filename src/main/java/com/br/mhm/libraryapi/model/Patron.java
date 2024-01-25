package com.br.mhm.libraryapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "PATRON")
@Getter
@Setter
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Email(regexp = ".+[@].+[\\.].+")
    @Column(name = "email")
    private String email;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "patron")
    private List<BorrowingRecord> borrowingRecord;
}
