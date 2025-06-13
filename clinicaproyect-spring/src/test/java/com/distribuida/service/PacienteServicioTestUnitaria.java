package com.distribuida.service;

import com.distribuida.dao.PacienteRepository;
import com.distribuida.model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PacienteServicioTestUnitaria {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    private Paciente paciente;

    @BeforeEach
    public void setUp() {
        paciente = new Paciente();
        paciente.setIdPaciente(1);
        paciente.setCedula("1754399507");
        paciente.setNombre("Angel");
        paciente.setApellido("Flores");
        paciente.setFechaNacimiento(new Date());
        paciente.setDireccion("Av. pacifico");
        paciente.setTelefono("098370160");
        paciente.setCorreo("Angel@ejemplo.com");
    }

    @Test
    public void testFindAll() {
        when(pacienteRepository.findAll()).thenReturn(List.of(paciente));
        List<Paciente> pacientes = pacienteService.findAll();
        assertNotNull(pacientes);
        assertEquals(1, pacientes.size());
        verify(pacienteRepository, times(1)).findAll();
    }

    @Test
    public void testFindOneExistente() {
        when(pacienteRepository.findById(1)).thenReturn(Optional.of(this.paciente));
        Paciente paciente = pacienteService.findOne(1);
        assertNotNull(paciente);
        assertEquals("Angel", paciente.getNombre());
    }

    @Test
    public void testFindOneNoExistente() {
        when(pacienteRepository.findById(2)).thenReturn(Optional.empty());
        Paciente paciente = pacienteService.findOne(2);
        assertNull(paciente);
    }

    @Test
    public void testSave() {
        when(pacienteRepository.save(paciente)).thenReturn(paciente);
        Paciente paciente1 = pacienteService.save(paciente);
        assertNotNull(paciente1);
        assertEquals("Angel", paciente1.getNombre());
    }

    @Test
    public void testUpdateExistente() {
        Paciente pacienteActualizado = new Paciente();
        pacienteActualizado.setCedula("1754399507");
        pacienteActualizado.setNombre("Angel");
        pacienteActualizado.setApellido("Flores");
        pacienteActualizado.setFechaNacimiento(new Date());
        pacienteActualizado.setDireccion("Calle noverias");
        pacienteActualizado.setTelefono("0983701160");
        pacienteActualizado.setCorreo("Angel@ejemplo.com");

        when(pacienteRepository.findById(1)).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(any())).thenReturn(pacienteActualizado);
        Paciente pacienteResultado = pacienteService.update(1, pacienteActualizado);
        assertNotNull(pacienteResultado);
        assertEquals("Angel", pacienteResultado.getNombre());
        verify(pacienteRepository, times(1)).save(paciente);
    }

    @Test
    public void testUpdateNoExistente() {
        Paciente pacienteNuevo = new Paciente();
        when(pacienteRepository.findById(2)).thenReturn(Optional.empty());
        Paciente paciente = pacienteService.update(2, pacienteNuevo);
        assertNull(paciente);
        verify(pacienteRepository, never()).save(any());
    }

    @Test
    public void testDeleteExistente() {
        when(pacienteRepository.existsById(1)).thenReturn(true);
        pacienteService.delete(1);
        verify(pacienteRepository).deleteById(1);
    }

    @Test
    public void testDeleteNoExistente() {
        when(pacienteRepository.existsById(2)).thenReturn(false);
        pacienteService.delete(2);
        verify(pacienteRepository, never()).deleteById(anyInt());
    }
}
