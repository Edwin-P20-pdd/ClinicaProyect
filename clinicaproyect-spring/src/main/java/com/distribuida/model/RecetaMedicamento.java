package com.distribuida.model;

import jakarta.persistence.*;

@Entity
@Table(name = "receta_medicamento")
public class RecetaMedicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_receta_medicamento")
    private int idRecetaMedicamento;
    @ManyToOne
    @JoinColumn(name = "id_receta")
    private Receta receta;
    @ManyToOne
    @JoinColumn(name = "id_medicamento")
    private Medicamento medicamento;
    public RecetaMedicamento() {
    }

    public RecetaMedicamento(int idRecetaMedicamento, Receta receta, Medicamento medicamento) {
        this.idRecetaMedicamento = idRecetaMedicamento;
        this.receta = receta;
        this.medicamento = medicamento;
    }

    public int getIdRecetaMedicamento() {
        return idRecetaMedicamento;
    }

    public void setIdRecetaMedicamento(int idRecetaMedicamento) {
        this.idRecetaMedicamento = idRecetaMedicamento;
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
        return "RecetaMedicamento{" +
                "idRecetaMedicamento=" + idRecetaMedicamento +
                ", receta=" + receta +
                ", medicamento=" + medicamento +
                '}';
    }
}
