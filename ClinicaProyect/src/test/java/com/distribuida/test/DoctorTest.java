package com.distribuida.test;

import com.distribuida.entities.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorTest {

    private Doctor doctor;

    @BeforeEach
    public void setup(){
        doctor = new Doctor(1001, "María", "Gómez", "Pediatría", "0991122334", "maria.gomez@clinica.com");
    }

    @Test
    public void TestDoctorConstructorAndGetters(){

        assertAll("Validar datos Doctor, Constructor y Getters",
                () -> assertEquals(1001, doctor.getIdDoctor()),
                () -> assertEquals("María", doctor.getNombre()),
                () -> assertEquals("Gómez", doctor.getApellido()),
                () -> assertEquals("Pediatría", doctor.getEspecialidad()),
                () -> assertEquals("0991122334", doctor.getTelefono()),
                () -> assertEquals("maria.gomez@clinica.com", doctor.getCorreo())
        );

    }

    @Test
    public void testDoctorSetters(){

        doctor.setIdDoctor(2);
        doctor.setNombre("Carlos");
        doctor.setApellido("Ramírez");
        doctor.setEspecialidad("Cardiología");
        doctor.setTelefono("0988112233");
        doctor.setCorreo("carlos.ramirez@clinica.com");

        assertAll("Validar datos actualizados del Doctor",
                () -> assertEquals(2, doctor.getIdDoctor()),
                () -> assertEquals("Carlos", doctor.getNombre()),
                () -> assertEquals("Ramírez", doctor.getApellido()),
                () -> assertEquals("Cardiología", doctor.getEspecialidad()),
                () -> assertEquals("0988112233", doctor.getTelefono()),
                () -> assertEquals("carlos.ramirez@clinica.com", doctor.getCorreo())
        );

    }

    @Test
    public void testDoctorToString(){
        String str = doctor.toString();
        assertAll("Validar Datos Doctor - ToString",
                () -> assertTrue(str.contains("1001")),
                () -> assertTrue(str.contains("María")),
                () -> assertTrue(str.contains("Gómez")),
                () -> assertTrue(str.contains("Pediatría")),
                () -> assertTrue(str.contains("0991122334")),
                () -> assertTrue(str.contains("maria.gomez@clinica.com"))
        );


    }

}
