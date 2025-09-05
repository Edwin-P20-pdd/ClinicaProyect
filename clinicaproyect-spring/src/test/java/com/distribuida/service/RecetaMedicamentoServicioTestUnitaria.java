package com.distribuida.service;

import com.distribuida.dao.MedicamentoRepository;
import com.distribuida.dao.RecetaMedicamentoRepository;
import com.distribuida.dao.RecetaRepository;
import com.distribuida.model.Medicamento;
import com.distribuida.model.Receta;
import com.distribuida.model.RecetaMedicamento;
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
public class RecetaMedicamentoServicioTestUnitaria {

    @Mock
    private RecetaMedicamentoRepository recetaMedicamentoRepository;

    @Mock
    private RecetaRepository recetaRepository;

    @Mock
    private MedicamentoRepository medicamentoRepository;

    @InjectMocks
    private RecetaMedicamentoServiceImpl recetaMedicamentoService;

    private Receta receta;
    private Medicamento medicamento;
    private RecetaMedicamento recetaMedicamento;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        receta = new Receta(1, "Tomar despues de cada comida", new Date(), null);
        medicamento = new Medicamento(1, "Ibuprofeno", "200mg", "Analgésico y relajante");
        recetaMedicamento = new RecetaMedicamento(1, receta, medicamento);
    }

    @Test
    public void testFindAll() {
        when(recetaMedicamentoRepository.findAll()).thenReturn(Arrays.asList(recetaMedicamento));
        List<RecetaMedicamento> lista = recetaMedicamentoService.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
        verify(recetaMedicamentoRepository, times(1)).findAll();
    }

    @Test
    public void testFindOne() {
        when(recetaMedicamentoRepository.findById(1)).thenReturn(Optional.of(recetaMedicamento));
        RecetaMedicamento resultado = recetaMedicamentoService.findOne(1);
        assertNotNull(resultado);
        assertEquals("Ibuprofeno", resultado.getMedicamento().getNombre());
        verify(recetaMedicamentoRepository, times(1)).findById(1);
    }

    @Test
    public void testSave() {
        when(recetaMedicamentoRepository.save(recetaMedicamento)).thenReturn(recetaMedicamento);
        RecetaMedicamento resultado = recetaMedicamentoService.save(recetaMedicamento);
        assertNotNull(resultado);
        assertEquals("Ibuprofeno", resultado.getMedicamento().getNombre());
        verify(recetaMedicamentoRepository, times(1)).save(recetaMedicamento);
    }

    @Test
    public void testUpdate() {
        Medicamento nuevoMedicamento = new Medicamento(2, "Ibuprofeno", "200mg", "Antiinflamatorio");
        RecetaMedicamento actualizado = new RecetaMedicamento(1, receta, nuevoMedicamento);

        when(recetaMedicamentoRepository.findById(1)).thenReturn(Optional.of(recetaMedicamento));
        when(recetaRepository.findById(1)).thenReturn(Optional.of(receta));
        when(medicamentoRepository.findById(2)).thenReturn(Optional.of(nuevoMedicamento));
        when(recetaMedicamentoRepository.save(any(RecetaMedicamento.class))).thenReturn(actualizado);

        RecetaMedicamento resultado = recetaMedicamentoService.update(1, actualizado);
        assertNotNull(resultado);
        assertEquals("Ibuprofeno", resultado.getMedicamento().getNombre());
        verify(recetaMedicamentoRepository).save(any(RecetaMedicamento.class));
    }

    @Test
    public void testDelete() {
        when(recetaMedicamentoRepository.existsById(1)).thenReturn(false);
        recetaMedicamentoService.delete(1);
        verify(recetaMedicamentoRepository, times(0)).deleteById(1);
    }
}
