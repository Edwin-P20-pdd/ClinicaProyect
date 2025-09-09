package com.distribuida.controller;

import com.distribuida.model.Doctor;
import com.distribuida.service.DoctorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DoctorController.class)
public class DoctorControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAll() throws Exception {
        Doctor doctor = new Doctor(1, "Luis", "Flores", "pulmonoria", "0991234567", "luis.ramirez@hospital.com");

        Mockito.when(doctorService.findAll()).thenReturn(List.of(doctor));

        mockMvc.perform(get("/doctores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Luis"));
    }

    @Test
    public void testSave() throws Exception {
        Doctor doctor = new Doctor(1, "Luis", "Flores", "pulmonoria", "0991234567", "luis.ramirez@hospital.com");

        Mockito.when(doctorService.save(any(Doctor.class))).thenReturn(doctor);

        mockMvc.perform(post("/doctores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Luis"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/doctores/1"))
                .andExpect(status().isNoContent());
    }
}
