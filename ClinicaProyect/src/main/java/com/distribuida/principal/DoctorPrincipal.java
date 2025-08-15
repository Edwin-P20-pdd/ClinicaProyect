package com.distribuida.principal;

import com.distribuida.entities.Doctor;

public class DoctorPrincipal {

    public static void main(String[] args) {

        Doctor doctor = new Doctor();

        doctor.setIdDoctor(1001);
        doctor.setNombre("María");
        doctor.setApellido("Gómez");
        doctor.setEspecialidad("Pediatría");
        doctor.setTelefono("0991122334");
        doctor.setCorreo("maria.gomez@clinica.com");

        System.out.println(doctor.toString());
    }
}
