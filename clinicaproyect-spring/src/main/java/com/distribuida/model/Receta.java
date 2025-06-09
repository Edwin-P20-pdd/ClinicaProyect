package com.distribuida.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "receta")
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_receta")
    private int idReceta;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha")
    private Date fecha;
    //
    @ManyToOne
    @JoinColumn(name = "id_cita")
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
