package com.distribuida.controller;

import com.distribuida.model.Cita;
import com.distribuida.model.Doctor;
import com.distribuida.model.Paciente;
import com.distribuida.model.Receta;
import com.distribuida.service.RecetaService;
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

public class RecetaControllerTestUnitaria {

    @InjectMocks
    private RecetaController recetaController;

    @Mock
    private RecetaService recetaService;

    private Receta receta;
    private Cita cita;
    private Paciente paciente;
    private Doctor doctor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        paciente = new Paciente();
        paciente.setIdPaciente(1);
        paciente.setNombre("María");
        paciente.setApellido("ramirez");
        paciente.setCedula("1234567890");
        paciente.setDireccion("Av. Guayaquil");
        paciente.setTelefono("0991234515");
        paciente.setCorreo("mariar@correo.com");

        doctor = new Doctor();
        doctor.setIdDoctor(1);
        doctor.setNombre("Luis");
        doctor.setApellido("Martínez");
        doctor.setEspecialidad("Pediatría");
        doctor.setTelefono("0987654321");
        doctor.setCorreo("luis@medico.com");

        cita = new Cita();
        cita.setIdCita(1);
        cita.setFecha(new Date());
        cita.setMotivo("Dolor de cabeza");
        cita.setPaciente(paciente);
        cita.setDoctor(doctor);

        receta = new Receta();
        receta.setIdReceta(1);
        receta.setDescripcion("Paracetamol 500mg cada 8 horas");
        receta.setFecha(new Date());
        receta.setCita(cita);
    }

    @Test
    public void testFindAll() {
        when(recetaService.findAll()).thenReturn(List.of(receta));
        ResponseEntity<List<Receta>> respuesta = recetaController.findAll();
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals(1, respuesta.getBody().size());
        verify(recetaService, times(1)).findAll();
    }

    @Test
    public void testFindOneExistente() {
        when(recetaService.findOne(1)).thenReturn(receta);
        ResponseEntity<Receta> respuesta = recetaController.findOne(1);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Paracetamol 500mg cada 8 horas", respuesta.getBody().getDescripcion());
    }

    @Test
    public void testFindOneNoExistente() {
        when(recetaService.findOne(2)).thenReturn(null);
        ResponseEntity<Receta> respuesta = recetaController.findOne(2);
        assertEquals(404, respuesta.getStatusCodeValue());
    }

    @Test
    public void testSave() {
        when(recetaService.save(any(Receta.class))).thenReturn(receta);
        ResponseEntity<Receta> respuesta = recetaController.save(receta);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Paracetamol 500mg cada 8 horas", respuesta.getBody().getDescripcion());
    }

    @Test
    public void testUpdateExistente() {
        when(recetaService.update(eq(1), any(Receta.class))).thenReturn(receta);
        ResponseEntity<Receta> respuesta = recetaController.update(1, receta);
        assertEquals(200, respuesta.getStatusCodeValue());
    }

    @Test
    public void testUpdateNoExistente() {
        when(recetaService.update(eq(2), any(Receta.class))).thenReturn(null);
        ResponseEntity<Receta> respuesta = recetaController.update(2, receta);
        assertEquals(404, respuesta.getStatusCodeValue());
    }

    @Test
    public void testDelete() {
        doNothing().when(recetaService).delete(1);
        ResponseEntity<Void> respuesta = recetaController.delete(1);
        assertEquals(204, respuesta.getStatusCodeValue());
        verify(recetaService, times(1)).delete(1);
    }
}
