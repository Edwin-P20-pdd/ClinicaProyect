package com.distribuida.test;

import com.distribuida.entities.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PacienteTest {

    private Paciente paciente;
    private Date fechaNacimiento;

    @BeforeEach
    public void setup(){
        fechaNacimiento = new Date();
        paciente = new Paciente(0001,"1728461972","Juan","Cruz", fechaNacimiento, "Av. Siempre viva", "0987654321", "correo@ejemplo.com");
    }

    @Test
    public void TestPacienteConstructorAndGetters(){

        assertAll("Validar datos Paciente, Constructor y Getters",
                () -> assertEquals(0001, paciente.getIdPaciente()),
                () -> assertEquals("1728461972", paciente.getCedula()),
                () -> assertEquals("Juan", paciente.getNombre()),
                () -> assertEquals("Cruz", paciente.getApellido()),
                //() -> assertEquals(new Date(), paciente.getFechaNacimiento()),
                () -> assertEquals("Av. Siempre viva", paciente.getDireccion()),
                () -> assertEquals("0987654321", paciente.getTelefono()),
                () -> assertEquals("correo@ejemplo.com", paciente.getCorreo())
        );
    }

    @Test
    public void testPacienteSetters(){

        paciente.setIdPaciente(2);
        paciente.setCedula("17284619722");
        paciente.setNombre("Juan2");
        paciente.setApellido("Cruz2");
        //paciente.setFechaNacimiento(new Date());
        paciente.setDireccion("Av. Siempre viva2");
        paciente.setTelefono("09876543212");
        paciente.setCorreo("correo2@ejemplo.com");

        assertAll("Validar datos Paciente, Setters",
                () -> assertEquals(2, paciente.getIdPaciente()),
                () -> assertEquals("17284619722", paciente.getCedula()),
                () -> assertEquals("Juan2", paciente.getNombre()),
                () -> assertEquals("Cruz2", paciente.getApellido()),
                //() -> assertEquals(new Date(), paciente.getFechaNacimiento()),
                () -> assertEquals("Av. Siempre viva2", paciente.getDireccion()),
                () -> assertEquals("09876543212", paciente.getTelefono()),
                () -> assertEquals("correo2@ejemplo.com", paciente.getCorreo())

        );
    }

    @Test
    public void testPacienteToString(){
        String str = paciente.toString();
        assertAll("Validar Datos Paciente - ToString",
                () -> assertTrue(str.contains("1")),
                () -> assertTrue(str.contains("1728461972")),
                () -> assertTrue(str.contains("Juan")),
                () -> assertTrue(str.contains("Cruz")),
                () -> assertTrue(str.contains(fechaNacimiento.toString())),
                () -> assertTrue(str.contains("Av. Siempre viva")),
                () -> assertTrue(str.contains("0987654321")),
                () -> assertTrue(str.contains("correo@ejemplo.com"))
        );
    }

}
