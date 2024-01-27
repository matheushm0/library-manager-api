package com.br.mhm.libraryapi.controller;

import com.br.mhm.libraryapi.model.Patron;
import com.br.mhm.libraryapi.service.PatronService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> savePatron(@RequestBody @Valid Patron patron, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errors);
        }

        return new ResponseEntity<>(patronService.savePatron(patron), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatron(@PathVariable Long id, @RequestBody @Valid Patron patron, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errors);
        }

        if (patronService.findPatronById(id).isEmpty())
            return ResponseEntity.notFound().build();

        patron.setId(id);

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
