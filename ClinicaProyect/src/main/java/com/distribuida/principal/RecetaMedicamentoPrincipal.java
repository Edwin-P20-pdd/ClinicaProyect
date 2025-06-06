package com.distribuida.principal;

import com.distribuida.entities.Medicamento;
import com.distribuida.entities.Receta;
import com.distribuida.entities.RecetaMedicamento;

public class RecetaMedicamentoPrincipal {

    public static void main(String[] args) {

        RecetaMedicamento rm = new RecetaMedicamento();
        Receta receta = new Receta();
        Medicamento medicamento = new Medicamento();

        rm.setReceta(receta);
        rm.setMedicamento(medicamento);

        System.out.println(rm.toString());
    }
}
