package com.distribuida.entities;

import java.util.Date;

public class Cita {

    private int idCita;
    private Date fecha;
    private String motivo;
    //
    private Paciente paciente;
    private Doctor doctor;

    public Cita() {
    }

    public Cita(int idCita, Date fecha, String motivo, Paciente paciente, Doctor doctor) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.motivo = motivo;
        this.paciente = paciente;
        this.doctor = doctor;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "idCita=" + idCita +
                ", fecha=" + fecha +
                ", motivo='" + motivo + '\'' +
                ", paciente=" + paciente +
                ", doctor=" + doctor +
                '}';
    }
}
