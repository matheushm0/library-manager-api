package com.br.mhm.libraryapi.repository;

import com.br.mhm.libraryapi.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {}
