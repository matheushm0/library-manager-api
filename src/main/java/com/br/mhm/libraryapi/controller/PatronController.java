package com.br.mhm.libraryapi.controller;

import com.br.mhm.libraryapi.model.Patron;
import com.br.mhm.libraryapi.service.PatronService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    private final PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public ResponseEntity<List<Patron>> findAllPatrons() {
        return ResponseEntity.ok(patronService.findAllPatrons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> findPatronById(@PathVariable Long id) {
        Patron patron = patronService.findPatronById(id).orElse(null);

        return patron != null ? ResponseEntity.ok(patron) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Patron> savePatron(@RequestBody @Valid Patron patron) {
        return new ResponseEntity<>(patronService.savePatron(patron), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @RequestBody @Valid Patron patron) {
        if (patronService.findPatronById(id).isEmpty())
            return ResponseEntity.notFound().build();

        Patron patronUpdated = patronService.updatePatron(patron);

        return ResponseEntity.ok(patronUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatronById(@PathVariable Long id) {
        if (patronService.findPatronById(id).isEmpty())
            return ResponseEntity.notFound().build();

        patronService.deletePatronById(id);

        return ResponseEntity.noContent().build();
    }

}
