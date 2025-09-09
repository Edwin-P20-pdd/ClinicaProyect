package com.distribuida.dao;

import com.distribuida.model.Doctor;
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
public class DoctorRepositorioTestIntegracion {

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void findAll() {
        List<Doctor> doctores = doctorRepository.findAll();
        assertNotNull(doctores);
        assertTrue(doctores.size() >= 0);
        for (Doctor item : doctores) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void findOne() {
        Optional<Doctor> doctor = doctorRepository.findById(1); // Cambiar por un ID existente
        assertTrue(doctor.isPresent(), "El doctor con id = 1 debería existir");
        System.out.println(doctor.toString());
    }

    @Test
    public void save() {
        Doctor doctor = new Doctor();
        doctor.setNombre("Andrea");
        doctor.setApellido("Rivas");
        doctor.setEspecialidad("Cardiología");
        doctor.setTelefono("0991234567");
        doctor.setCorreo("andrea.rivas@hospital.com");

        doctorRepository.save(doctor);

        assertNotNull(doctor.getIdDoctor(), "El doctor guardado debe tener un id.");
        assertEquals("Andrea", doctor.getNombre());
        assertEquals("Cardiología", doctor.getEspecialidad());
    }

    @Test
    public void update() {
        Optional<Doctor> doctor = doctorRepository.findById(101); // Cambiar por un ID existente

        assertTrue(doctor.isPresent(), "El doctor con id = 101 debe existir para ser actualizado.");

        doctor.orElse(null).setNombre("Carlos Actualizado");
        doctor.orElse(null).setApellido("Pérez Modificado");
        doctor.orElse(null).setEspecialidad("Dermatología");
        doctor.orElse(null).setTelefono("0988888888");
        doctor.orElse(null).setCorreo("carlos.actualizado@mail.com");

        Doctor doctorActualizado = doctorRepository.save(doctor.orElse(null));

        assertEquals("Carlos Actualizado", doctorActualizado.getNombre());
        assertEquals("Dermatología", doctorActualizado.getEspecialidad());
    }

    @Test
    public void delete() {
        if (doctorRepository.existsById(100)) { // Cambiar por un ID existente
            doctorRepository.deleteById(100);
        }
        assertFalse(doctorRepository.existsById(100), "El id = 100 debería haberse eliminado.");
    }

}
