package com.distribuida.controller;

import com.distribuida.model.Medicamento;
import com.distribuida.model.Receta;
import com.distribuida.model.RecetaMedicamento;
import com.distribuida.service.RecetaMedicamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RecetaMedicamentoControllerTestUnitaria {

    @InjectMocks
    private RecetaMedicamentoController recetaMedicamentoController;

    @Mock
    private RecetaMedicamentoService recetaMedicamentoService;

    private RecetaMedicamento recetaMedicamento;
    private Receta receta;
    private Medicamento medicamento;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        receta = new Receta();
        receta.setIdReceta(1);
        receta.setDescripcion("Tratamiento para fiebre");
        receta.setFecha(new Date());

        medicamento = new Medicamento();
        medicamento.setIdMedicamento(1);
        medicamento.setNombre("Paracetamol");
        medicamento.setDosis("500mg");
        medicamento.setDescripcion("Analgésico y antipirético");

        recetaMedicamento = new RecetaMedicamento();
        recetaMedicamento.setIdRecetaMedicamento(1);
        recetaMedicamento.setReceta(receta);
        recetaMedicamento.setMedicamento(medicamento);
    }

    @Test
    public void testFindAll() {
        when(recetaMedicamentoService.findAll()).thenReturn(List.of(recetaMedicamento));
        ResponseEntity<List<RecetaMedicamento>> respuesta = recetaMedicamentoController.findAll();
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals(1, respuesta.getBody().size());
        verify(recetaMedicamentoService, times(1)).findAll();
    }

    @Test
    public void testFindOneExistente() {
        when(recetaMedicamentoService.findOne(1)).thenReturn(recetaMedicamento);
        ResponseEntity<RecetaMedicamento> respuesta = recetaMedicamentoController.findOne(1);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Paracetamol", respuesta.getBody().getMedicamento().getNombre());
    }

    @Test
    public void testFindOneNoExistente() {
        when(recetaMedicamentoService.findOne(2)).thenReturn(null);
        ResponseEntity<RecetaMedicamento> respuesta = recetaMedicamentoController.findOne(2);
        assertEquals(404, respuesta.getStatusCodeValue());
    }

    @Test
    public void testSave() {
        when(recetaMedicamentoService.save(any(RecetaMedicamento.class))).thenReturn(recetaMedicamento);
        ResponseEntity<RecetaMedicamento> respuesta = recetaMedicamentoController.save(recetaMedicamento);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Paracetamol", respuesta.getBody().getMedicamento().getNombre());
    }

    @Test
    public void testUpdateExistente() {
        when(recetaMedicamentoService.update(eq(1), any(RecetaMedicamento.class)))
                .thenReturn(recetaMedicamento);
        ResponseEntity<RecetaMedicamento> respuesta = recetaMedicamentoController.update(1, recetaMedicamento);
        assertEquals(200, respuesta.getStatusCodeValue());
    }

    @Test
    public void testUpdateNoExistente() {
        when(recetaMedicamentoService.update(eq(2), any(RecetaMedicamento.class)))
                .thenReturn(null);
        ResponseEntity<RecetaMedicamento> respuesta = recetaMedicamentoController.update(2, recetaMedicamento);
        assertEquals(404, respuesta.getStatusCodeValue());
    }

    @Test
    public void testDelete() {
        doNothing().when(recetaMedicamentoService).delete(1);
        ResponseEntity<Void> respuesta = recetaMedicamentoController.delete(1);
        assertEquals(204, respuesta.getStatusCodeValue());
        verify(recetaMedicamentoService, times(1)).delete(1);
    }
}
