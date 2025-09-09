package com.distribuida.controller;

import com.distribuida.model.Medicamento;
import com.distribuida.service.MedicamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MedicamentoControllerTestUnitaria {

    @InjectMocks
    private MedicamentoController medicamentoController;

    @Mock
    private MedicamentoService medicamentoService;

    private Medicamento medicamento;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        medicamento = new Medicamento();
        medicamento.setIdMedicamento(1);
        medicamento.setNombre("Paracetamol");
        medicamento.setDosis("500mg");
        medicamento.setDescripcion("Alivia el dolor y la fiebre");
    }
    //
     //

    @Test
    public void testFindAll() {
        when(medicamentoService.findAll()).thenReturn(List.of(medicamento));
        ResponseEntity<List<Medicamento>> respuesta = medicamentoController.findAll();
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals(1, respuesta.getBody().size());
        verify(medicamentoService, times(1)).findAll();
    }

    @Test
    public void testFindOneExistente() {
        when(medicamentoService.findOne(1)).thenReturn(medicamento);
        ResponseEntity<Medicamento> respuesta = medicamentoController.findOne(1);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Paracetamol", respuesta.getBody().getNombre());
    }

    @Test
    public void testFindOneNoExistente() {
        when(medicamentoService.findOne(2)).thenReturn(null);
        ResponseEntity<Medicamento> respuesta = medicamentoController.findOne(2);
        assertEquals(404, respuesta.getStatusCodeValue());
    }

    @Test
    public void testSave() {
        when(medicamentoService.save(any(Medicamento.class))).thenReturn(medicamento);
        ResponseEntity<Medicamento> respuesta = medicamentoController.save(medicamento);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Paracetamol", respuesta.getBody().getNombre());
    }

    @Test
    public void testUpdateExistente() {
        when(medicamentoService.update(eq(1), any(Medicamento.class))).thenReturn(medicamento);
        ResponseEntity<Medicamento> respuesta = medicamentoController.update(1, medicamento);
        assertEquals(200, respuesta.getStatusCodeValue());
    }

    @Test
    public void testUpdateNoExistente() {
        when(medicamentoService.update(eq(2), any(Medicamento.class))).thenReturn(null);
        ResponseEntity<Medicamento> respuesta = medicamentoController.update(2, medicamento);
        assertEquals(404, respuesta.getStatusCodeValue());
    }

    @Test
    public void testDelete() {
        doNothing().when(medicamentoService).delete(1);
        ResponseEntity<Void> respuesta = medicamentoController.delete(1);
        assertEquals(204, respuesta.getStatusCodeValue());
        verify(medicamentoService, times(1)).delete(1);
    }
}
