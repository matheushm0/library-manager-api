package com.br.mhm.libraryapi.controller;

import com.br.mhm.libraryapi.model.Patron;
import com.br.mhm.libraryapi.service.PatronService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatronController.class)
public class PatronControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    Patron PATRON_1 = new Patron(1L, "John Doe", "johndoe@test.com", "123456789", "123 Main St");
    Patron PATRON_2 = new Patron(2L, "Jane Doe", "janedoe@test.com", "987654321", "456 Main St");

    public PatronControllerTest(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void findAllPatrons_shouldReturnListOfPatrons() throws Exception {
        List<Patron> patrons = Arrays.asList(PATRON_1, PATRON_2);
        when(patronService.findAllPatrons()).thenReturn(patrons);

        mockMvc.perform(get("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void findPatronById_shouldReturnPatronWhenExists() throws Exception {
        Long patronId = 1L;
        when(patronService.findPatronById(patronId)).thenReturn(Optional.of(PATRON_1));

        mockMvc.perform(get("/api/patrons/{id}", patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void findPatronById_shouldReturnNotFoundWhenDoesNotExist() throws Exception {
        Long patronId = 1L;
        when(patronService.findPatronById(patronId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/patrons/{id}", patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void savePatron_shouldReturnCreated() throws Exception {
        when(patronService.savePatron(any(Patron.class))).thenReturn(PATRON_1);

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"email\":\"johndoe@test.com\",\"phoneNumber\":\"123456789\",\"address\":\"123 Main St\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void updatePatron_shouldReturnOkWhenExists() throws Exception {
        Long patronId = 1L;
        Patron updatedPatron = new Patron(patronId, "Updated Name", "updated@test.com", "987654321", "Updated Address");

        when(patronService.findPatronById(patronId)).thenReturn(Optional.of(PATRON_1));
        when(patronService.updatePatron(any(Patron.class))).thenReturn(updatedPatron);

        mockMvc.perform(put("/api/patrons/{id}", patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Name\",\"email\":\"updated@test.com\",\"phoneNumber\":\"987654321\",\"address\":\"Updated Address\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.email").value("updated@test.com"))
                .andExpect(jsonPath("$.phoneNumber").value("987654321"))
                .andExpect(jsonPath("$.address").value("Updated Address"));
    }

    @Test
    void updatePatron_shouldReturnNotFoundWhenDoesNotExist() throws Exception {
        Long patronId = 1L;
        when(patronService.findPatronById(patronId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/patrons/{id}", patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Name\",\"email\":\"updated@test.com\",\"phoneNumber\":\"987654321\",\"address\":\"Updated Address\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePatronById_shouldReturnNoContentWhenExists() throws Exception {
        Long patronId = 1L;
        when(patronService.findPatronById(patronId)).thenReturn(Optional.of(PATRON_1));

        mockMvc.perform(delete("/api/patrons/{id}", patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePatronById_shouldReturnNotFoundWhenDoesNotExist() throws Exception {
        Long patronId = 1L;
        when(patronService.findPatronById(patronId)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/patrons/{id}", patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
