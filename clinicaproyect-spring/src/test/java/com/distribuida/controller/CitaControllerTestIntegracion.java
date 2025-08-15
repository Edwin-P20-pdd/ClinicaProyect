package com.distribuida.controller;

import com.distribuida.model.Cita;
import com.distribuida.model.Doctor;
import com.distribuida.model.Paciente;
import com.distribuida.service.CitaService;
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

@WebMvcTest(CitaController.class)
public class CitaControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CitaService citaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAll() throws Exception {
        Paciente paciente = new Paciente(1, "1758639502", "Juan", "Lopes", new Date(), "Av. Guayaquil", "0977777777", "jlopez@correo.com");
        Doctor doctor = new Doctor(1, "Pedro", "Villa", "Psicologia", "0988888888", "pedro@medico.com");

        Cita cita = new Cita(1, new Date(), "Consulta semanal", paciente, doctor);

        Mockito.when(citaService.findAll()).thenReturn(List.of(cita));

        mockMvc.perform(get("/citas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].motivo").value("Consulta semanal"));
    }

    @Test
    public void testSave() throws Exception {
        Paciente paciente = new Paciente(1, "1758639502", "Juan", "Lopes", new Date(), "Av. Guayaquil", "0977777777", "jlopez@correo.com");
        Doctor doctor = new Doctor(1, "Pedro", "Villa", "Psicologia", "0988888888", "pedro@medico.com");

        Cita cita = new Cita(0, new Date(), "Consulta semanal", paciente, doctor);

        Mockito.when(citaService.save(any(Cita.class))).thenReturn(cita);

        mockMvc.perform(post("/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cita)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.motivo").value("Consulta semanal"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/citas/1"))
                .andExpect(status().isNoContent());
    }
}
