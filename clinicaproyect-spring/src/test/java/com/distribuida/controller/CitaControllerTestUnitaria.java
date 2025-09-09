package com.distribuida.controller;

import com.distribuida.model.Cita;
import com.distribuida.model.Doctor;
import com.distribuida.model.Paciente;
import com.distribuida.service.CitaService;
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

public class CitaControllerTestUnitaria {

    @InjectMocks
    private CitaController citaController;

    @Mock
    private CitaService citaService;

    private Cita cita;
    private Paciente paciente;
    private Doctor doctor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        paciente = new Paciente();
        paciente.setIdPaciente(1);
        paciente.setNombre("Lisbeth");
        paciente.setApellido("Perez");
        paciente.setCedula("0943424234");
        paciente.setDireccion("Calle medio por alla");
        paciente.setTelefono("0986574410");
        paciente.setCorreo("Lperez@correo.com");

        doctor = new Doctor();
        doctor.setIdDoctor(1);
        doctor.setNombre("Carlos");
        doctor.setApellido("Ramírez");
        doctor.setEspecialidad("Cardiología");
        doctor.setTelefono("0991234567");
        doctor.setCorreo("carlos@medico.com");

        cita = new Cita();
        cita.setIdCita(1);
        cita.setFecha(new Date());
        cita.setMotivo("Control general");
        cita.setPaciente(paciente);
        cita.setDoctor(doctor);
    }

    @Test
    public void testFindAll() {
        when(citaService.findAll()).thenReturn(List.of(cita));
        ResponseEntity<List<Cita>> respuesta = citaController.findAll();
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals(1, respuesta.getBody().size());
        verify(citaService, times(1)).findAll();
    }

    @Test
    public void testFindOneExistente() {
        when(citaService.findOne(1)).thenReturn(cita);
        ResponseEntity<Cita> respuesta = citaController.findOne(1);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Control general", respuesta.getBody().getMotivo());
    }

    @Test
    public void testFindOneNoExistente() {
        when(citaService.findOne(2)).thenReturn(null);
        ResponseEntity<Cita> respuesta = citaController.findOne(2);
        assertEquals(404, respuesta.getStatusCodeValue());
    }

    @Test
    public void testSave() {
        when(citaService.save(any(Cita.class))).thenReturn(cita);
        ResponseEntity<Cita> respuesta = citaController.save(cita);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Control general", respuesta.getBody().getMotivo());
    }

    @Test
    public void testUpdateExistente() {
        when(citaService.update(eq(1), any(Cita.class))).thenReturn(cita);
        ResponseEntity<Cita> respuesta = citaController.update(1, cita);
        assertEquals(200, respuesta.getStatusCodeValue());
    }

    @Test
    public void testUpdateNoExistente() {
        when(citaService.update(eq(2), any(Cita.class))).thenReturn(null);
        ResponseEntity<Cita> respuesta = citaController.update(2, cita);
        assertEquals(404, respuesta.getStatusCodeValue());
    }

//    @Test
//    public void testDelete() {
//        doNothing().when(citaService).delete(1);
//        ResponseEntity<Void> respuesta = citaController.delete(1);
//        assertEquals(204, respuesta.getStatusCodeValue());
//        verify(citaService, times(1)).delete(1);
//    }
}
