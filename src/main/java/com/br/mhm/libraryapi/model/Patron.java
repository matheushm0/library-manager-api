package com.br.mhm.libraryapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "PATRON")
@Getter
@Setter
@NoArgsConstructor
public class Patron {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Email cannot be null")
    @Email(regexp = ".+[@].+[\\.].+", message = "Invalid email format. Please provide a valid email address")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Phone number cannot be null")
    @Size(min = 1, max = 20, message = "Phone number must be between 1 and 20 characters")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull(message = "Address cannot be null")
    @Size(min = 1, max = 255, message = "Address must be between 1 and 255 characters")
    @Column(name = "address")
    private String address;

    @JsonIgnoreProperties({"patron"})
    @OneToMany(mappedBy = "patron")
    private List<BorrowingRecord> borrowingRecord;

    public Patron(Long id, String name, String email, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
