package com.distribuida.test;

import com.distribuida.entities.Medicamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MedicamentoTest {

    private Medicamento medicamento;

    @BeforeEach
    public void setup(){

        medicamento = new Medicamento(4001, "Ibuprofeno", "200 mg", "Tomar una tableta cada 8 horas después de las comidas.");

    }

    @Test
    public void testMedicamentoConstructorAndGetters(){

        assertAll("Validar datos Medicamento, Constructor y Getters",
                () -> assertEquals(4001, medicamento.getIdMedicamento()),
                () -> assertEquals("Ibuprofeno", medicamento.getNombre()),
                () -> assertEquals("200 mg", medicamento.getDosis()),
                () -> assertEquals("Tomar una tableta cada 8 horas después de las comidas.", medicamento.getDescripcion())
        );

    }

    @Test
    public void testMedicamentoSetters() {
        medicamento.setIdMedicamento(4002);
        medicamento.setNombre("Paracetamol");
        medicamento.setDosis("500 mg");
        medicamento.setDescripcion("Tomar una tableta cada 6 horas si persiste el dolor.");

        assertAll("Validar datos Medicamento, Setters",
                () -> assertEquals(4002, medicamento.getIdMedicamento()),
                () -> assertEquals("Paracetamol", medicamento.getNombre()),
                () -> assertEquals("500 mg", medicamento.getDosis()),
                () -> assertEquals("Tomar una tableta cada 6 horas si persiste el dolor.", medicamento.getDescripcion())
        );
    }

    @Test
    public void testMedicamentoToString() {
        String str = medicamento.toString();
        assertAll("Validar toString de Medicamento",
                () -> assertTrue(str.contains("4001")),
                () -> assertTrue(str.contains("Ibuprofeno")),
                () -> assertTrue(str.contains("200 mg")),
                () -> assertTrue(str.contains("Tomar una tableta cada 8 horas después de las comidas."))
        );
    }



}
