package com.distribuida.principal;

import com.distribuida.entities.Cita;
import com.distribuida.entities.Doctor;
import com.distribuida.entities.Paciente;

import java.util.Date;

public class CitaPrincipal {

    public static void main(String[] args) {

        Cita cita = new Cita();
        Paciente paciente = new Paciente();
        Doctor doctor = new Doctor();

        cita.setIdCita(2001);
        cita.setFecha(new Date());
        cita.setMotivo("Dolor de cabeza persistente");
        cita.setPaciente(paciente);
        cita.setDoctor(doctor);

        System.out.println(cita.toString());
    }
}
