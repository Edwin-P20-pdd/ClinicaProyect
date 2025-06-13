package com.distribuida.service;

import com.distribuida.dao.DoctorRepository;
import com.distribuida.model.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorServicioTestUnitaria {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    private Doctor doctor;

    @BeforeEach
    public void setUp() {
        doctor = new Doctor();
        doctor.setIdDoctor(1);
        doctor.setNombre("Angel");
        doctor.setApellido("Flores");
        doctor.setEspecialidad("Cardiologo");
        doctor.setTelefono("0983701160");
        doctor.setCorreo("Angel@ejemplo.com");
    }

    @Test
    public void testFindAll() {
        when(doctorRepository.findAll()).thenReturn(List.of(doctor));
        List<Doctor> doctores = doctorService.findAll();
        assertNotNull(doctores);
        assertEquals(1, doctores.size());
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    public void testFindOneExistente() {
        when(doctorRepository.findById(1)).thenReturn(Optional.of(this.doctor));
        Doctor doctor = doctorService.findOne(1);
        assertNotNull(doctor);
        assertEquals("Angel", doctor.getNombre());
    }

    @Test
    public void testFindOneNoExistente() {
        when(doctorRepository.findById(2)).thenReturn(Optional.empty());
        Doctor doctor = doctorService.findOne(2);
        assertNull(doctor);
    }

    @Test
    public void testSave() {
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        Doctor doctor1 = doctorService.save(doctor);
        assertNotNull(doctor1);
        assertEquals("Angel", doctor1.getNombre());
    }

    @Test
    public void testUpdateExistente() {
        Doctor doctorActualizado = new Doctor();
        doctorActualizado.setNombre("Angel");
        doctorActualizado.setApellido("Flores");
        doctorActualizado.setEspecialidad("Corazon");
        doctorActualizado.setTelefono("0983701160");
        doctorActualizado.setCorreo("Angel@ejemplo.com");

        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(any())).thenReturn(doctorActualizado);
        Doctor doctorResultado = doctorService.update(1, doctorActualizado);
        assertNotNull(doctorResultado);
        assertEquals("Angel", doctorResultado.getNombre());
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    public void testUpdateNoExistente() {
        Doctor doctorNuevo = new Doctor();
        when(doctorRepository.findById(2)).thenReturn(Optional.empty());
        Doctor doctor = doctorService.update(2, doctorNuevo);
        assertNull(doctor);
        verify(doctorRepository, never()).save(any());
    }

    @Test
    public void testDeleteExistente() {
        when(doctorRepository.existsById(1)).thenReturn(true);
        doctorService.delete(1);
        verify(doctorRepository).deleteById(1);
    }

    @Test
    public void testDeleteNoExistente() {
        when(doctorRepository.existsById(2)).thenReturn(false);
        doctorService.delete(2);
        verify(doctorRepository, never()).deleteById(anyInt());
    }
}