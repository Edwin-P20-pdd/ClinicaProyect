package com.distribuida.controller;

import com.distribuida.model.Cita;
import com.distribuida.model.Doctor;
import com.distribuida.model.Paciente;
import com.distribuida.model.Receta;
import com.distribuida.service.RecetaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecetaController.class)
public class RecetaControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecetaService recetaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAll() throws Exception {
        Paciente paciente = new Paciente(1, "1234567890", "Maria", "Ramirez", new Date(), "Av. Guayaquil", "0988888888", "jramirez@correo.com");
        Doctor doctor = new Doctor(1, "Carlos", "Ramírez", "Pediatría", "0988888888", "carlos@medico.com");
        Cita cita = new Cita(1, new Date(), "Dolor de cabeza", paciente, doctor);
        Receta receta = new Receta(1, "Tomar paracetamol cada 8h", new Date(), cita);

        Mockito.when(recetaService.findAll()).thenReturn(List.of(receta));

        mockMvc.perform(get("/recetas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descripcion").value("Tomar paracetamol cada 8h"));
    }

    @Test
    public void testSave() throws Exception {
        Paciente paciente = new Paciente(1, "1234567890", "Maria", "Ramirez", new Date(), "Av. Guayaquil", "0988888888", "jramirez@correo.com");
        Doctor doctor = new Doctor(1, "Carlos", "Ramírez", "Pediatría", "0988888888", "carlos@medico.com");
        Cita cita = new Cita(1, new Date(), "Dolor de cabeza", paciente, doctor);
        Receta receta = new Receta(0, "Tomar paracetamol cada 8h", new Date(), cita);

        Mockito.when(recetaService.save(any(Receta.class))).thenReturn(receta);

        mockMvc.perform(post("/recetas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(receta)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Tomar paracetamol cada 8h"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/recetas/1"))
                .andExpect(status().isNoContent());
    }
}
