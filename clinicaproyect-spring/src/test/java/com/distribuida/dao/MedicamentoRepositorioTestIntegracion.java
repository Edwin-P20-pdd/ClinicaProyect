package com.distribuida.dao;

import com.distribuida.model.Medicamento;
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
public class MedicamentoRepositorioTestIntegracion {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Test
    public void findAll() {
        List<Medicamento> medicamentos = medicamentoRepository.findAll();
        assertNotNull(medicamentos);
        assertTrue(medicamentos.size() >= 0);
        for (Medicamento item : medicamentos) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void findOne() {
        Optional<Medicamento> medicamento = medicamentoRepository.findById(1); // Cambiar por un ID existente
        assertTrue(medicamento.isPresent(), "El medicamento con id = 1 debería existir");
        System.out.println(medicamento.toString());
    }

    @Test
    public void save() {
        Medicamento medicamento = new Medicamento();
        medicamento.setNombre("Paracetamol");
        medicamento.setDosis("500mg");
        medicamento.setDescripcion("Analgésico y antipirético");

        medicamentoRepository.save(medicamento);

        assertNotNull(medicamento.getIdMedicamento(), "El medicamento guardado debe tener un id.");
        assertEquals("Paracetamol", medicamento.getNombre());
        assertEquals("500mg", medicamento.getDosis());
    }

    @Test
    public void update() {
        Optional<Medicamento> medicamento = medicamentoRepository.findById(101); // Cambiar por un ID existente

        assertTrue(medicamento.isPresent(), "El medicamento con id = 101 debe existir para ser actualizado.");

        medicamento.orElse(null).setNombre("Ibuprofeno");
        medicamento.orElse(null).setDosis("400mg");
        medicamento.orElse(null).setDescripcion("Antiinflamatorio y analgésico");

        Medicamento medicamentoActualizado = medicamentoRepository.save(medicamento.orElse(null));

        assertEquals("Ibuprofeno", medicamentoActualizado.getNombre());
        assertEquals("400mg", medicamentoActualizado.getDosis());
    }

    @Test
    public void delete() {
        if (medicamentoRepository.existsById(100)) { // Cambiar por un ID existente
            medicamentoRepository.deleteById(100);
        }
        assertFalse(medicamentoRepository.existsById(100), "El id = 100 debería haberse eliminado.");
    }

}
