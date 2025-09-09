package com.distribuida.principal;

import com.distribuida.entities.Cita;
import com.distribuida.entities.Receta;

import java.util.Date;

public class RecetaPrincipal {

    public static void main(String[] args) {

        Receta receta = new Receta();
        Cita cita = new Cita();

        receta.setIdReceta(3001);
        receta.setDescripcion("Paracetamol 500mg cada 8 horas por 5 días.");
        receta.setFecha(new Date());

        receta.setCita(cita);

        System.out.println(receta.toString());
    }
}
