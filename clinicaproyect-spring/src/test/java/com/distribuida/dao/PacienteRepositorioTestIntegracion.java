package com.distribuida.dao;

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
public class PacienteRepositorioTestIntegracion {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Test
    public void findAll() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        assertNotNull(pacientes);
        assertTrue(pacientes.size() >= 0);
        for (Paciente item : pacientes) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void findOne() {
        Optional<Paciente> paciente = pacienteRepository.findById(1); // Cambia por un ID existente
        assertTrue(paciente.isPresent(), "El paciente con id = 1 debería existir");
        System.out.println(paciente.toString());
    }

    @Test
    public void save() {
        Paciente paciente = new Paciente(0,"1723456789", "Luis", "Martínez", new Date(), "Calle Ficticia 123", "0987654321", "luis.martinez@gmail.com");
        paciente.setCedula("1723456789");
        paciente.setNombre("Luis");
        paciente.setApellido("Martínez");
        paciente.setFechaNacimiento(new Date());
        paciente.setDireccion("Calle Ficticia 123");
        paciente.setTelefono("0987654321");
        paciente.setCorreo("luis.martinez@gmail.com");

        pacienteRepository.save(paciente);

        assertNotNull(paciente.getIdPaciente(), "El paciente guardado debe tener un id.");
        assertEquals("1723456789", paciente.getCedula());
        assertEquals("Luis", paciente.getNombre());
    }

    @Test
    public void update() {
        Optional<Paciente> paciente = pacienteRepository.findById(10); // Cambia por un ID existente

        assertTrue(paciente.isPresent(), "El paciente con id = 101 debe existir para ser actualizado.");

        paciente.orElse(null).setCedula("0987654321");
        paciente.orElse(null).setNombre("Luis Actualizado");
        paciente.orElse(null).setApellido("Martínez Modificado");
        paciente.orElse(null).setFechaNacimiento(new Date());
        paciente.orElse(null).setDireccion("Av. Siempre Viva 742");
        paciente.orElse(null).setTelefono("0999999999");
        paciente.orElse(null).setCorreo("actualizado@mail.com");

        Paciente pacienteActualizado = pacienteRepository.save(paciente.orElse(null));

        assertEquals("Luis Actualizado", pacienteActualizado.getNombre());
        assertEquals("Martínez Modificado", pacienteActualizado.getApellido());
    }

    @Test
    public void delete() {
        if (pacienteRepository.existsById(101)) { // Cambia por un ID existente
            pacienteRepository.deleteById(101);
        }
        assertFalse(pacienteRepository.existsById(101), "El id = 100 debería haberse eliminado.");
    }

}
