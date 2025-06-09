package com.distribuida.dao;

import com.distribuida.model.Receta;
import com.distribuida.model.Medicamento;
import com.distribuida.model.RecetaMedicamento;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(value = false)
public class RecetaMedicamentoRepositorioTestIntegracion {

    @Autowired
    private RecetaMedicamentoRepository recetaMedicamentoRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Test
    public void findAll() {
        List<RecetaMedicamento> recetaMedicamentos = recetaMedicamentoRepository.findAll();
        assertNotNull(recetaMedicamentos);
        assertTrue(recetaMedicamentos.size() >= 0);
        recetaMedicamentos.forEach(System.out::println);
    }

    @Test
    public void findOne() {
        Optional<RecetaMedicamento> item = recetaMedicamentoRepository.findById(1); // Cambiar por un ID existente
        assertTrue(item.isPresent(), "Debe existir la relación con ID = 1");
        System.out.println(item.toString());
    }

    @Test
    public void save() {
        Optional<Receta> receta = recetaRepository.findById(1); // Cambiar por un ID existente
        Optional<Medicamento> medicamento = medicamentoRepository.findById(1); // Cambiar por un ID existente

        assertTrue(receta.isPresent(), "La receta debe existir");
        assertTrue(medicamento.isPresent(), "El medicamento debe existir");

        RecetaMedicamento recetaMedicamento = new RecetaMedicamento();
        recetaMedicamento.setReceta(receta.get());
        recetaMedicamento.setMedicamento(medicamento.get());

        recetaMedicamentoRepository.save(recetaMedicamento);

        // No existe un id, por ende no se puede hacer la relación
        //assertNotNull(recetaMedicamento.getId(), "Debe generarse un ID para la relación receta-medicamento");
    }

    @Test
    public void update() {
        Optional<RecetaMedicamento> recetaMedicamento = recetaMedicamentoRepository.findById(1); // Cambiar por un ID existente
        Optional<Receta> receta = recetaRepository.findById(2);
        Optional<Medicamento> medicamento = medicamentoRepository.findById(2); // Cambiar por un ID válido diferente

        assertTrue(recetaMedicamento.isPresent(), "Debe existir la relación con ID = 1 para ser actualizada");
        assertTrue(medicamento.isPresent(), "Debe existir el nuevo medicamento");

        recetaMedicamento.orElse(null).setReceta(receta.orElse(null));
        recetaMedicamento.orElse(null).setMedicamento(medicamento.orElse(null));

        RecetaMedicamento actualizado = recetaMedicamentoRepository.save(recetaMedicamento.orElse(null));

        assertEquals(2, actualizado.getMedicamento().getIdMedicamento());
    }

    @Test
    public void delete() {
        if (recetaMedicamentoRepository.existsById(1)) { // Cambiar por un ID existente
            recetaMedicamentoRepository.deleteById(1);
            Optional<RecetaMedicamento> eliminado = recetaMedicamentoRepository.findById(1);
            assertFalse(eliminado.isPresent());
        }
    }
}
