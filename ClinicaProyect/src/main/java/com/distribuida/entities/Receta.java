package com.distribuida.entities;

import java.util.Date;

public class Receta {

    private int idReceta;
    private String descripcion;
    private Date fecha;
    //
    private Cita cita;

    public Receta() {
    }

    public Receta(int idReceta, String descripcion, Date fecha, Cita cita) {
        this.idReceta = idReceta;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.cita = cita;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    @Override
    public String toString() {
        return "Receta{" +
                "idReceta=" + idReceta +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", cita=" + cita +
                '}';
    }
}
