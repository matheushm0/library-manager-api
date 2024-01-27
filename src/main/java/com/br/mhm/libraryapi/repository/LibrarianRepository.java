package com.br.mhm.libraryapi.repository;

import com.br.mhm.libraryapi.model.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, Long> {

    public Optional<Librarian> findByEmail(String email);

}
