package com.distribuida.controller;

import com.distribuida.model.Medicamento;
import com.distribuida.service.MedicamentoService;
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

@WebMvcTest(MedicamentoController.class)
public class MedicamentoControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicamentoService medicamentoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAll() throws Exception {
        Medicamento medicamento = new Medicamento(1, "Paracetamol", "500mg", "Analgésico y antipirético");

        Mockito.when(medicamentoService.findAll()).thenReturn(List.of(medicamento));

        mockMvc.perform(get("/medicamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Paracetamol"));
    }

    @Test
    public void testSave() throws Exception {
        Medicamento medicamento = new Medicamento(0, "Paracetamol", "500mg", "Analgésico y antipirético");

        Mockito.when(medicamentoService.save(any(Medicamento.class))).thenReturn(medicamento);

        mockMvc.perform(post("/medicamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medicamento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Paracetamol"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/medicamentos/1"))
                .andExpect(status().isNoContent());
    }
}
