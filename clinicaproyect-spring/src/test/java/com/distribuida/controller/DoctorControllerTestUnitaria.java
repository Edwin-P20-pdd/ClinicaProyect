package com.distribuida.controller;

import com.distribuida.model.Doctor;
import com.distribuida.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DoctorControllerTestUnitaria {

    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private DoctorService doctorService;

    private Doctor doctor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        doctor = new Doctor();
        doctor.setIdDoctor(1);
        doctor.setNombre("Anderson");
        doctor.setApellido("Muñoz");
        doctor.setEspecialidad("Psicologia");
        doctor.setTelefono("0986745863");
        doctor.setCorreo("andersonM@hospital.com");
    }

    @Test
    public void testFindAll() {
        when(doctorService.findAll()).thenReturn(List.of(doctor));
        ResponseEntity<List<Doctor>> respuesta = doctorController.findAll();
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals(1, respuesta.getBody().size());
        verify(doctorService, times(1)).findAll();
    }

    @Test
    public void testFindOneExistente() {
        when(doctorService.findOne(1)).thenReturn(doctor);
        ResponseEntity<Doctor> respuesta = doctorController.findOne(1);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Anderson", respuesta.getBody().getNombre());
    }

    @Test
    public void testFindOneNoExistente() {
        when(doctorService.findOne(2)).thenReturn(null);
        ResponseEntity<Doctor> respuesta = doctorController.findOne(2);
        assertEquals(404, respuesta.getStatusCodeValue());
    }

    @Test
    public void testSave() {
        when(doctorService.save(any(Doctor.class))).thenReturn(doctor);
        ResponseEntity<Doctor> respuesta = doctorController.save(doctor);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Andrés", respuesta.getBody().getNombre());
    }

    @Test
    public void testUpdateExistente() {
        when(doctorService.update(eq(1), any(Doctor.class))).thenReturn(doctor);
        ResponseEntity<Doctor> respuesta = doctorController.update(1, doctor);
        assertEquals(200, respuesta.getStatusCodeValue());
    }

    @Test
    public void testUpdateNoExistente() {
        when(doctorService.update(eq(2), any(Doctor.class))).thenReturn(null);
        ResponseEntity<Doctor> respuesta = doctorController.update(2, doctor);
        assertEquals(404, respuesta.getStatusCodeValue());
    }

    @Test
    public void testDelete() {
        doNothing().when(doctorService).delete(1);
        ResponseEntity<Void> respuesta = doctorController.delete(1);
        assertEquals(204, respuesta.getStatusCodeValue());
        verify(doctorService, times(1)).delete(1);
    }
}
