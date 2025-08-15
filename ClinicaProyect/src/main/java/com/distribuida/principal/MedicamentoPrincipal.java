package com.distribuida.principal;

import com.distribuida.entities.Medicamento;

public class MedicamentoPrincipal {

    public static void main(String[] args) {

        Medicamento medicamento = new Medicamento();

        medicamento.setIdMedicamento(4001);
        medicamento.setNombre("Ibuprofeno");
        medicamento.setDosis("200 mg");
        medicamento.setDescripcion("Tomar una tableta cada 8 horas después de las comidas.");

        System.out.println(medicamento.toString());
    }
}
