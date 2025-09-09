package com.distribuida.controller;

import com.distribuida.model.Paciente;
import com.distribuida.service.PacienteService;
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

public class PacienteControllerTestUnitaria {

    @InjectMocks
    private PacienteController pacienteController;

    @Mock
    private PacienteService pacienteService;

    private Paciente paciente;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        paciente = new Paciente();
        paciente.setIdPaciente(1);
        paciente.setCedula("1579859755");
        paciente.setNombre("Laura");
        paciente.setApellido("Lopez");
        paciente.setFechaNacimiento(new Date());
        paciente.setDireccion("Av. Tumbaco");
        paciente.setTelefono("0978485972");
        paciente.setCorreo("laural@correo.com");
    }

    @Test
    public void testFindAll() {
        when(pacienteService.findAll()).thenReturn(List.of(paciente));
        ResponseEntity<List<Paciente>> respuesta = pacienteController.findAll();
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals(1, respuesta.getBody().size());
        verify(pacienteService, times(1)).findAll();
    }

    @Test
    public void testFindOneExistente() {
        when(pacienteService.findOne(1)).thenReturn(paciente);
        ResponseEntity<Paciente> respuesta = pacienteController.findOne(1);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Laura", respuesta.getBody().getNombre());
    }

    @Test
    public void testFindOneNoExistente() {
        when(pacienteService.findOne(2)).thenReturn(null);
        ResponseEntity<Paciente> respuesta = pacienteController.findOne(2);
        assertEquals(404, respuesta.getStatusCodeValue());
    }

    @Test
    public void testSave() {
        when(pacienteService.save(any(Paciente.class))).thenReturn(paciente);
        ResponseEntity<Paciente> respuesta = pacienteController.save(paciente);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Laura", respuesta.getBody().getNombre());
    }

    @Test
    public void testUpdateExistente() {
        when(pacienteService.update(eq(1), any(Paciente.class))).thenReturn(paciente);
        ResponseEntity<Paciente> respuesta = pacienteController.update(1, paciente);
        assertEquals(200, respuesta.getStatusCodeValue());
    }

    @Test
    public void testUpdateNoExistente() {
        when(pacienteService.update(eq(2), any(Paciente.class))).thenReturn(null);
        ResponseEntity<Paciente> respuesta = pacienteController.update(2, paciente);
        assertEquals(404, respuesta.getStatusCodeValue());
    }

    @Test
    public void testDelete() {
        doNothing().when(pacienteService).delete(1);
        ResponseEntity<Void> respuesta = pacienteController.delete(1);
        assertEquals(204, respuesta.getStatusCodeValue());
        verify(pacienteService, times(1)).delete(1);
    }
}
