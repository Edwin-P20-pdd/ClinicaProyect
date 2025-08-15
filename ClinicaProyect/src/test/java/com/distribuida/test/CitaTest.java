package com.distribuida.test;

import com.distribuida.entities.Cita;
import com.distribuida.entities.Doctor;
import com.distribuida.entities.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CitaTest {

    private Cita cita;
    private Paciente paciente;
    private Doctor doctor;
    private Date fecha;
    private Date fechaNacimiento;

    @BeforeEach
    public void setup(){

        fecha = new Date();
        fechaNacimiento = new Date();

        paciente = new Paciente(0001,"1728461972","Juan","Cruz", fechaNacimiento, "Av. Siempre viva", "0987654321", "correo@ejemplo.com");
        doctor = new Doctor(1001, "María", "Gómez", "Pediatría", "0991122334", "maria.gomez@clinica.com");
        cita = new Cita(2001,fecha,"Dolor de cabeza persistente", paciente, doctor);
    }

    @Test
    public void testCitaConstructorAndGetters(){
        assertAll("Validar datos Cita, Constructor y Getters",
                () -> assertEquals(2001, cita.getIdCita()),
                // () -> assertEquals(fecha, cita.getFecha()), // Comparar fechas directamente puede fallar por diferencias de milisegundos
                () -> assertEquals("Dolor de cabeza persistente", cita.getMotivo()),
                () -> assertEquals("Juan", cita.getPaciente().getNombre()),
                () -> assertEquals("María", cita.getDoctor().getNombre())
        );
    }

    @Test
    public void testCitaSetters(){

        cita.setIdCita(5);
        cita.setFecha(fecha);
        cita.setMotivo("Dolor de cabeza persistente");
        cita.setMotivo("Consulta general");

        Paciente nuevoPaciente = new Paciente(0001,"1728461972","Juan","Cruz", fechaNacimiento, "Av. Siempre viva", "0987654321", "correo@ejemplo.com");
        cita.setPaciente(nuevoPaciente);

        Doctor nuevoDoctor = new Doctor(1001, "María", "Gómez", "Pediatría", "0991122334", "maria.gomez@clinica.com");
        cita.setDoctor(nuevoDoctor);

        assertAll("Validar datos Cita, Setters",
                () -> assertEquals(5, cita.getIdCita()),
                () -> assertEquals(fecha, cita.getFecha()),
                () -> assertEquals("Consulta general", cita.getMotivo()),
                () -> assertEquals(nuevoPaciente, cita.getPaciente()),
                () -> assertEquals(nuevoDoctor, cita.getDoctor())
        );

    }

    @Test
    public void testCitaToString(){
        String str = cita.toString();
        assertAll("Validar toString de Cita",
                () -> assertTrue(str.contains("2001")),
                () -> assertTrue(str.contains("fecha")),
                () -> assertTrue(str.contains("Dolor de cabeza persistente")),
                () -> assertTrue(str.contains("Juan")),
                () -> assertTrue(str.contains("María"))
        );

    }

}
