package com.distribuida.dao;

import com.distribuida.model.Cita;
import com.distribuida.model.Receta;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
public class RecetaRepositorioTestIntegracion {

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Test
    public void findAll() {
        List<Receta> recetas = recetaRepository.findAll();
        assertNotNull(recetas);
        assertTrue(recetas.size() >= 0);
        for (Receta item : recetas) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void findOne() {
        Optional<Receta> receta = recetaRepository.findById(1); // Cambiar por un ID existente
        assertTrue(receta.isPresent(), "La receta con id = 1 debería existir");
        System.out.println(receta.toString());
    }

    //Test Duduoso con el metodo Constructor
    @Test
    public void save() {
        Optional<Cita> cita = citaRepository.findById(1); // Cambiar por un ID existente

        assertTrue(cita.isPresent(), "Cita no encontrada");

        Receta receta = new Receta();
        receta.setDescripcion("Tomar una cápsula diaria por 7 días");
        receta.setFecha(new Date());
        receta.setCita(cita.get());

        recetaRepository.save(receta);

        assertNotNull(receta.getIdReceta(), "La receta guardada debe tener un id.");
        assertEquals("Tomar una cápsula diaria por 7 días", receta.getDescripcion());
    }

    @Test
    public void update() {
        Optional<Receta> receta = recetaRepository.findById(101); // Cambiar por un ID existente
        Optional<Cita> cita = citaRepository.findById(1);
        assertTrue(receta.isPresent(), "La receta con id = 101 debe existir para ser actualizada.");

        receta.orElse(null).setDescripcion("Tomar cada 8 horas después de las comidas");
        receta.orElse(null).setFecha(new Date());
        receta.orElse(null).setCita(cita.orElse(null));
        Receta recetaActualizada = recetaRepository.save(receta.orElse(null));

        assertEquals("Tomar cada 8 horas después de las comidas", recetaActualizada.getDescripcion());
    }

    @Test
    public void delete() {
        if (recetaRepository.existsById(100)) { // Cambiar por un ID existente
            recetaRepository.deleteById(100);
            Optional<Receta> recetaEliminada = recetaRepository.findById(100);
            assertFalse(recetaEliminada.isPresent());
        }
    }
}
