package com.distribuida.service;

import com.distribuida.dao.CitaRepository;
import com.distribuida.dao.DoctorRepository;
import com.distribuida.dao.PacienteRepository;
import com.distribuida.model.Cita;
import com.distribuida.model.Doctor;
import com.distribuida.model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CitaServicioTestUnitaria {

    @Mock
    private CitaRepository citaRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private CitaServiceImpl citaService;

    private Cita cita;
    private Paciente paciente;
    private Doctor doctor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        paciente = new Paciente(1, "1234567890", "Juan", "Pérez", new Date(), "Av. Siempre Viva", "0924789233", "juan@mail.com");
        doctor = new Doctor(1, "Dra. María", "Gomez", "Cardiología", "09346457567", "maría@clinica.com");

        cita = new Cita(1, new Date(), "Consulta general", paciente, doctor);
    }

    @Test
    public void testFindAll() {
        when(citaRepository.findAll()).thenReturn(Arrays.asList(cita));
        List<Cita> citas = citaService.findAll();
        assertNotNull(citas);
        assertEquals(1, citas.size());
        verify(citaRepository, times(1)).findAll();
    }

    @Test
    public void testFindOne() {
        when(citaRepository.findById(1)).thenReturn(Optional.of(cita));
        Cita resultado = citaService.findOne(1);
        assertNotNull(resultado);
        assertEquals("Consulta general", resultado.getMotivo());
        verify(citaRepository, times(1)).findById(1);
    }

    @Test
    public void testSave() {
        when(citaRepository.save(cita)).thenReturn(cita);
        Cita resultado = citaService.save(cita);
        assertNotNull(resultado);
        assertEquals("Consulta general", resultado.getMotivo());
        verify(citaRepository, times(1)).save(cita);
    }

    @Test
    public void testUpdate() {
        Cita citaActualizada = new Cita(1, new Date(), "Chequeo anual", paciente, doctor);

        when(citaRepository.findById(1)).thenReturn(Optional.of(cita));
        when(pacienteRepository.findById(1)).thenReturn(Optional.of(paciente));
        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
        when(citaRepository.save(any(Cita.class))).thenReturn(citaActualizada);

        Cita resultado = citaService.update(1, citaActualizada);
        assertNotNull(resultado);
        assertEquals("Chequeo anual", resultado.getMotivo());
        verify(citaRepository).save(any(Cita.class));
    }

    @Test
    public void testDelete() {
        when(citaRepository.existsById(1)).thenReturn(false);
        citaService.delete(1);
        verify(citaRepository, times(0)).deleteById(1);
    }
}
