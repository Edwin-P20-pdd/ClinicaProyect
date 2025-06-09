package com.distribuida.model;

import jakarta.persistence.*;

@Entity
@Table(name = "receta_medicamento")
public class RecetaMedicamento {
    @ManyToOne
    @JoinColumn(name = "id_receta")
    private Receta receta;
    @ManyToOne
    @JoinColumn(name = "id_medicamento")
    private Medicamento medicamento;

    public RecetaMedicamento() {
    }

    public RecetaMedicamento(Receta receta, Medicamento medicamento) {
        this.receta = receta;
        this.medicamento = medicamento;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    @Override
    public String toString() {
        return "RecetaMedica{" +
                "receta=" + receta +
                ", medicamento=" + medicamento +
                '}';
    }
}
