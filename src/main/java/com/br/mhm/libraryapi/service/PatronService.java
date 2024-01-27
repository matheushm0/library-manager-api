package com.br.mhm.libraryapi.service;

import com.br.mhm.libraryapi.model.Patron;
import com.br.mhm.libraryapi.repository.PatronRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    private static final Logger logger = LoggerFactory.getLogger(PatronService.class);

    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    @Cacheable("patrons")
    public List<Patron> findAllPatrons() {
        logger.info("Find all patrons");

        return patronRepository.findAll();
    }

    @Cacheable("patron")
    public Optional<Patron> findPatronById(Long id) {
        logger.info("Find patron by id: {}", id);

        return patronRepository.findById(id);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "patrons", allEntries = true),
            @CacheEvict(cacheNames = "patron", allEntries = true)
    })
    public Patron savePatron(Patron patron) {
        logger.info("Save patron: {}", patron.getName());

        return patronRepository.save(patron);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "patrons", allEntries = true),
            @CacheEvict(cacheNames = "patron", allEntries = true)
    })
    public Patron updatePatron(Patron patron) {
        logger.info("Update patron: {}", patron.getId());

        return patronRepository.save(patron);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "patrons", allEntries = true),
            @CacheEvict(cacheNames = "patron", allEntries = true)
    })
    public void deletePatronById(Long id) {
        logger.info("Delete patron by id: {}", id);

        patronRepository.deleteById(id);
    }
}
