package com.distribuida.dao;

import com.distribuida.model.Cita;
import com.distribuida.model.Doctor;
import com.distribuida.model.Paciente;
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
public class CitaRepositorioTestIntegracion {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void findAll() {
        List<Cita> citas = citaRepository.findAll();
        assertNotNull(citas);
        assertTrue(citas.size() >= 0);
        for (Cita item : citas) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void findOne() {
        Optional<Cita> cita = citaRepository.findById(1); // Cambiar por un ID existente
        assertTrue(cita.isPresent(), "La cita con id = 1 debería existir");
        System.out.println(cita.toString());
    }

    @Test
    public void save() {
        Optional<Paciente> paciente = pacienteRepository.findById(1); // Cambiar por un ID existente
        Optional<Doctor> doctor = doctorRepository.findById(1);       // Cambiar por un ID existente

        assertTrue(paciente.isPresent(), "Paciente no encontrado");
        assertTrue(doctor.isPresent(), "Doctor no encontrado");

        Cita cita = new Cita();
        cita.setFecha(new Date());
        cita.setMotivo("Consulta general");
        cita.setPaciente(paciente.get());
        cita.setDoctor(doctor.get());

        citaRepository.save(cita);

        assertNotNull(cita.getIdCita(), "La cita guardada debe tener un id.");
        assertEquals("Consulta general", cita.getMotivo());
    }

    // No estan implementando los Foreign key
    @Test
    public void update() {
        Optional<Cita> cita = citaRepository.findById(101); // Cambiar por un ID existente

        assertTrue(cita.isPresent(), "La cita con id = 101 debe existir para ser actualizada.");

        cita.orElse(null).setFecha(new Date());
        cita.orElse(null).setMotivo("Seguimiento médico");

        Cita citaActualizada = citaRepository.save(cita.orElse(null));

        assertEquals("Seguimiento médico", citaActualizada.getMotivo());
    }

    @Test
    public void delete() {
        if (citaRepository.existsById(100)) { // Cambiar por un ID existente
            citaRepository.deleteById(100);
            Optional<Cita> citaEliminada = citaRepository.findById(100);
            assertFalse(citaEliminada.isPresent());
        }
        assertFalse(citaRepository.existsById(100), "La cita con id = 100 debería haberse eliminado.");
    }
}
