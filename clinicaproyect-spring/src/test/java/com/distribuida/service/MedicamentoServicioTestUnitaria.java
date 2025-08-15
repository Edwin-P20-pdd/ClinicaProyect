package com.distribuida.service;

import com.distribuida.dao.MedicamentoRepository;
import com.distribuida.model.Medicamento;
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
public class MedicamentoServicioTestUnitaria {

    @Mock
    private MedicamentoRepository medicamentoRepository;

    @InjectMocks
    private MedicamentoServiceImpl medicamentoService;

    private Medicamento medicamento;

    @BeforeEach
    public void setUp() {
        medicamento = new Medicamento();
        medicamento.setIdMedicamento(1);
        medicamento.setNombre("Ibuprofeno");
        medicamento.setDosis("200mg");
        medicamento.setDescripcion("Analgésico y relajante");
    }

    @Test
    public void testFindAll() {
        when(medicamentoRepository.findAll()).thenReturn(List.of(medicamento));
        List<Medicamento> medicamentos = medicamentoService.findAll();
        assertNotNull(medicamentos);
        assertEquals(1, medicamentos.size());
        verify(medicamentoRepository, times(1)).findAll();
    }

    @Test
    public void testFindOneExistente() {
        when(medicamentoRepository.findById(1)).thenReturn(Optional.of(this.medicamento));
        Medicamento medicamento = medicamentoService.findOne(1);
        assertNotNull(medicamento);
        assertEquals("Ibuprofeno", medicamento.getNombre());
    }

    @Test
    public void testFindOneNoExistente() {
        when(medicamentoRepository.findById(2)).thenReturn(Optional.empty());
        Medicamento medicamento = medicamentoService.findOne(2);
        assertNull(medicamento);
    }

    @Test
    public void testSave() {
        when(medicamentoRepository.save(medicamento)).thenReturn(medicamento);
        Medicamento medicamento1 = medicamentoService.save(medicamento);
        assertNotNull(medicamento1);
        assertEquals("Ibuprofeno", medicamento1.getNombre());
    }

    @Test
    public void testUpdateExistente() {
        Medicamento medicamentoActualizado = new Medicamento();
        medicamentoActualizado.setNombre("Ibuprofeno");
        medicamentoActualizado.setDosis("200mg");
        medicamentoActualizado.setDescripcion("Antiinflamatorio");

        when(medicamentoRepository.findById(1)).thenReturn(Optional.of(medicamento));
        when(medicamentoRepository.save(any())).thenReturn(medicamentoActualizado);
        Medicamento medicamentoResultado = medicamentoService.update(1, medicamentoActualizado);
        assertNotNull(medicamentoResultado);
        assertEquals("Ibuprofeno", medicamentoResultado.getNombre());
        verify(medicamentoRepository, times(1)).save(medicamento);
    }

    @Test
    public void testUpdateNoExistente() {
        Medicamento nuevo = new Medicamento();
        when(medicamentoRepository.findById(2)).thenReturn(Optional.empty());
        Medicamento medicamento = medicamentoService.update(2, nuevo);
        assertNull(medicamento);
        verify(medicamentoRepository, never()).save(any());
    }

    @Test
    public void testDeleteExistente() {
        when(medicamentoRepository.existsById(1)).thenReturn(true);
        medicamentoService.delete(1);
        verify(medicamentoRepository).deleteById(1);
    }

    @Test
    public void testDeleteNoExistente() {
        when(medicamentoRepository.existsById(2)).thenReturn(false);
        medicamentoService.delete(2);
        verify(medicamentoRepository, never()).deleteById(anyInt());
    }
}
