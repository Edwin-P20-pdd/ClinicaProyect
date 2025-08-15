package com.distribuida.model;

import jakarta.persistence.*;

@Entity
@Table(name = "medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicamento")
    private int idMedicamento;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "dosis")
    private String dosis;
    @Column(name = "descripcion")
    private String descripcion;

    public Medicamento() {
    }

    public Medicamento(int idMedicamento, String nombre, String dosis, String descripcion) {
        this.idMedicamento = idMedicamento;
        this.nombre = nombre;
        this.dosis = dosis;
        this.descripcion = descripcion;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Medicamento{" +
                "idMedicamento=" + idMedicamento +
                ", nombre='" + nombre + '\'' +
                ", dosis='" + dosis + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

}
