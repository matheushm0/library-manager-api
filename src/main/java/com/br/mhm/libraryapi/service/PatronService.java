package com.br.mhm.libraryapi.service;

import com.br.mhm.libraryapi.model.Patron;
import com.br.mhm.libraryapi.repository.PatronRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<Patron> findAllPatrons() {
        return patronRepository.findAll();
    }

    public Optional<Patron> findPatronById(Long id) {
        return patronRepository.findById(id);
    }

    public Patron savePatron(Patron patron) {
        return patronRepository.save(patron);
    }

    public Patron updatePatron(Patron patron) {
        return patronRepository.save(patron);
    }

    public void deletePatronById(Long id) {
        patronRepository.deleteById(id);
    }
}
