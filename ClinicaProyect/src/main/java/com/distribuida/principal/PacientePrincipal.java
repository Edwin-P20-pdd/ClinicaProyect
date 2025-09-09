package com.distribuida.principal;

import com.distribuida.entities.Paciente;

import java.util.Date;

public class PacientePrincipal {

    public static void main(String[] args) {

        Paciente paciente = new Paciente();

        paciente.setIdPaciente(0001);
        paciente.setCedula("1728461972");
        paciente.setNombre("Juan");
        paciente.setApellido("Cruz");
        paciente.setFechaNacimiento(new Date());
        paciente.setDireccion("Av. Siempre viva");
        paciente.setTelefono("0987654321");
        paciente.setCorreo("correo@ejemplo.com");

        System.out.println(paciente.toString());
    }
}
