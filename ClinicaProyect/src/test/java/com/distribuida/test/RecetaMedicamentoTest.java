package com.distribuida.test;

import com.distribuida.entities.Medicamento;
import com.distribuida.entities.Receta;
import com.distribuida.entities.RecetaMedicamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecetaMedicamentoTest {

    private Medicamento medicamento;
    private Receta receta;
    private RecetaMedicamento recetaMedicamento;

    @BeforeEach
    public void setup(){
        medicamento = new Medicamento();
        receta = new Receta();
        recetaMedicamento = new RecetaMedicamento(receta, medicamento);

    }

    @Test
    public void testRecetaMedicamentoConstructorAndGetters() {
        assertAll("Validar datos RecetaMedicamento, Constructor y Getters",
                () -> assertEquals(receta, recetaMedicamento.getReceta()),
                () -> assertEquals(medicamento, recetaMedicamento.getMedicamento())
        );
    }

    @Test
    public void testRecetaMedicamentoSetters() {
        Receta nuevaReceta = new Receta();
        Medicamento nuevoMedicamento = new Medicamento();

        recetaMedicamento.setReceta(nuevaReceta);
        recetaMedicamento.setMedicamento(nuevoMedicamento);

        assertAll("Validar datos RecetaMedicamento, Setters",
                () -> assertEquals(nuevaReceta, recetaMedicamento.getReceta()),
                () -> assertEquals(nuevoMedicamento, recetaMedicamento.getMedicamento())
        );
    }

    @Test
    public void testRecetaMedicamentoToString() {
        String str = recetaMedicamento.toString();
        assertAll("Validar toString de RecetaMedicamento",
                () -> assertTrue(str.contains(receta.toString())),
                () -> assertTrue(str.contains(medicamento.toString()))
        );
    }


}
