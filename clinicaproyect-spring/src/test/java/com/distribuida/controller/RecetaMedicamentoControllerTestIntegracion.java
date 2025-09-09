package com.distribuida.controller;

import com.distribuida.model.Medicamento;
import com.distribuida.model.Receta;
import com.distribuida.model.RecetaMedicamento;
import com.distribuida.service.RecetaMedicamentoService;
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

@WebMvcTest(RecetaMedicamentoController.class)
public class RecetaMedicamentoControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecetaMedicamentoService recetaMedicamentoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAll() throws Exception {
        Receta receta = new Receta(1, "Tomar cada 8h", new Date(), null);
        Medicamento medicamento = new Medicamento(1, "Ibuprofeno", "400mg", "Antiinflamatorio");
        RecetaMedicamento recetaMedicamento = new RecetaMedicamento(1, receta, medicamento);

        Mockito.when(recetaMedicamentoService.findAll()).thenReturn(List.of(recetaMedicamento));

        mockMvc.perform(get("/receta-medicamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].medicamento.nombre").value("Ibuprofeno"));
    }
    //comentario
    @Test
    public void testSave() throws Exception {
        Receta receta = new Receta(1, "Tomar cada 8h", new Date(), null);
        Medicamento medicamento = new Medicamento(1, "Ibuprofeno", "400mg", "Antiinflamatorio");
        RecetaMedicamento recetaMedicamento = new RecetaMedicamento(0, receta, medicamento);

        Mockito.when(recetaMedicamentoService.save(any(RecetaMedicamento.class))).thenReturn(recetaMedicamento);

        mockMvc.perform(post("/receta-medicamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recetaMedicamento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medicamento.nombre").value("Ibuprofeno"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/receta-medicamentos/1"))
                .andExpect(status().isNoContent());
    }
}
