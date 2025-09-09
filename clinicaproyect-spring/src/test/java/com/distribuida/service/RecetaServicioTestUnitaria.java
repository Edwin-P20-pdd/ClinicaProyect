package com.distribuida.service;

import com.distribuida.dao.CitaRepository;
import com.distribuida.dao.RecetaRepository;
import com.distribuida.model.Cita;
import com.distribuida.model.Receta;
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
public class RecetaServicioTestUnitaria {

    @Mock
    private RecetaRepository recetaRepository;

    @Mock
    private CitaRepository citaRepository;

    @InjectMocks
    private RecetaServiceImpl recetaService;

    private Receta receta;
    private Cita cita;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        cita = new Cita(1, new Date(), "Chequeo general", null, null);
        receta = new Receta(1, "Tomar despues de cada comida", new Date(), cita);
    }

    @Test
    public void testFindAll() {
        when(recetaRepository.findAll()).thenReturn(Arrays.asList(receta));
        List<Receta> recetas = recetaService.findAll();
        assertNotNull(recetas);
        assertEquals(1, recetas.size());
        verify(recetaRepository, times(1)).findAll();
    }

    @Test
    public void testFindOne() {
        when(recetaRepository.findById(1)).thenReturn(Optional.of(receta));
        Receta resultado = recetaService.findOne(1);
        assertNotNull(resultado);
        assertEquals("Tomar despues de cada comida", resultado.getDescripcion());
        verify(recetaRepository, times(1)).findById(1);
    }

    @Test
    public void testSave() {
        when(recetaRepository.save(receta)).thenReturn(receta);
        Receta resultado = recetaService.save(receta);
        assertNotNull(resultado);
        assertEquals("Tomar despues de cada comida", resultado.getDescripcion());
        verify(recetaRepository, times(1)).save(receta);
    }

    @Test
    public void testUpdate() {
        Receta recetaActualizada = new Receta(1, "Tomar despues de cada comida", new Date(), cita);

        when(recetaRepository.findById(1)).thenReturn(Optional.of(receta));
        when(citaRepository.findById(1)).thenReturn(Optional.of(cita));
        when(recetaRepository.save(any(Receta.class))).thenReturn(recetaActualizada);

        Receta resultado = recetaService.update(1, recetaActualizada);
        assertNotNull(resultado);
        assertEquals("Tomar despues de cada comida", resultado.getDescripcion());
        verify(recetaRepository).save(any(Receta.class));
    }

    @Test
    public void testDelete() {
        when(recetaRepository.existsById(1)).thenReturn(false);
        recetaService.delete(1);
        verify(recetaRepository, times(0)).deleteById(1);
    }
}
