package com.distribuida.controller;

import com.distribuida.model.Paciente;
import com.distribuida.service.PacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PacienteController.class)
public class PacienteControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteService pacienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAll() throws Exception {
        Date fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-15");
        Paciente paciente = new Paciente(1, "1784875922", "Josefa", "Chavez", fechaNacimiento, "Av. centro norte", "0978487269", "jchavez@correo.com");

        Mockito.when(pacienteService.findAll()).thenReturn(List.of(paciente));

        mockMvc.perform(get("/pacientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Josefa"));
    }

    @Test
    public void testSave() throws Exception {
        Date fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-15");
        Paciente paciente = new Paciente(0, "1784875922", "Josefa", "Chavez", fechaNacimiento, "Av. centro norte", "0978487269", "jchavez@correo.com");

        Mockito.when(pacienteService.save(any(Paciente.class))).thenReturn(paciente);

        mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("María"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/pacientes/1"))
                .andExpect(status().isNoContent());
    }
}
