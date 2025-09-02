package com.distribuida.controller;

import com.distribuida.model.Carrito;
import com.distribuida.service.CarritoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarritoGuestController.class)
public class CarritoGuestControllerTestIntegracion {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarritoService carritoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Carrito crearCarritoMock() {
        Carrito carrito = new Carrito();
        carrito.setIdCarrito(1L);
        carrito.setToken("farm123");
        carrito.setSubtotal(BigDecimal.valueOf(50));
        carrito.setDescuento(BigDecimal.ZERO);
        carrito.setImpuestos(BigDecimal.valueOf(6));
        carrito.setTotal(BigDecimal.valueOf(56));
        carrito.setActualizadoEn(LocalDateTime.now());
        return carrito;
    }

    @Test
    public void testCreateOrGetByPaciente() throws Exception {
        Carrito carrito = crearCarritoMock();

        Mockito.when(carritoService.getOrCreateByPacienteId(eq(1), eq("farm123")))
                .thenReturn(carrito);

        mockMvc.perform(post("/api/paciente/cart")
                        .param("pacienteId", "1")
                        .param("token", "farm123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("farm123"))
                .andExpect(jsonPath("$.total").value(56));
    }

    @Test
    public void testGetByPaciente() throws Exception {
        Carrito carrito = crearCarritoMock();

        Mockito.when(carritoService.getByPacienteId(eq(1)))
                .thenReturn(carrito);

        mockMvc.perform(get("/api/guest/cart")
                        .param("pacienteId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subtotal").value(50));
    }

    @Test
    public void testAddItemByPaciente() throws Exception {
        Carrito carrito = crearCarritoMock();

        Map<String, Integer> body = new HashMap<>();
        body.put("medicamentoId", 10);
        body.put("cantidad", 2);

        Mockito.when(carritoService.addItem(eq(1), eq(10), eq(2)))
                .thenReturn(carrito);

        mockMvc.perform(post("/api/guest/cart/items")
                        .param("pacienteId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("farm123"));
    }

    @Test
    public void testUpdateItemByguest() throws Exception {
        Carrito carrito = crearCarritoMock();

        Map<String, Integer> body = new HashMap<>();
        body.put("cantidad", 5);

        Mockito.when(carritoService.updateItemCantidad(eq(1), eq(7L), eq(5)))
                .thenReturn(carrito);

        mockMvc.perform(put("/api/guest/cart/items/{id}", 7L)
                        .param("pacienteId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(56));
    }

    @Test
    public void testRemoveItemByPaciente() throws Exception {
        Mockito.doNothing().when(carritoService).removeItem(1, 7L);

        mockMvc.perform(delete("/api/guest/cart/items/{id}", 7L)
                        .param("pacienteId", "1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testClearCartByPaciente() throws Exception {
        Mockito.doNothing().when(carritoService).clear(1);

        mockMvc.perform(delete("/api/guest/cart/clear")
                        .param("pacienteId", "1"))
                .andExpect(status().isNoContent());
    }
}
