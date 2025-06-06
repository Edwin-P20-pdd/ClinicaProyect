package com.distribuida.test;

import com.distribuida.entities.Cita;
import com.distribuida.entities.Receta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RecetaTest {

    private Receta receta;
    private Cita cita;
    private Date fecha;

    @BeforeEach
    public void setup(){
        fecha = new Date();
        cita = new Cita();
        receta = new Receta(3001, "Paracetamol 500mg cada 8 horas por 5 días.", fecha, cita);

    }

    @Test
    public void testRecetaConstructorAndGetters(){
        assertAll("Validar datos Receta, Constructor y Getters",
                () -> assertEquals(3001, receta.getIdReceta()),
                () -> assertEquals("Paracetamol 500mg cada 8 horas por 5 días.", receta.getDescripcion()),
                () -> assertEquals(fecha, receta.getFecha()),
                () -> assertEquals(cita, receta.getCita())
        );

    }

    @Test
    public void testRecetaSetters() throws Exception {

        receta.setIdReceta(3002);
        receta.setDescripcion("Ibuprofeno 400mg cada 8 horas por 3 días.");
        receta.setFecha(fecha);
        Cita nuevaCita = new Cita();
        nuevaCita.setIdCita(2002);
        receta.setCita(nuevaCita);

        assertAll("Validar datos Receta, Setters",
                () -> assertEquals(3002, receta.getIdReceta()),
                () -> assertEquals("Ibuprofeno 400mg cada 8 horas por 3 días.", receta.getDescripcion()),
                () -> assertEquals(fecha, receta.getFecha()),
                () -> assertEquals(nuevaCita, receta.getCita())
        );
    }

    @Test
    public void testRecetaToString() {
        String str = receta.toString();

        assertAll("Validar toString de Receta",
                () -> assertTrue(str.contains("3001")),
                () -> assertTrue(str.contains("Paracetamol 500mg cada 8 horas por 5 días.")),
                () -> assertTrue(str.contains(fecha.toString())),
                () -> assertTrue(str.contains("Cita"))
        );
    }

}
