package com.distribuida.entities;

public class RecetaMedicamento {

    private Receta receta;
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
