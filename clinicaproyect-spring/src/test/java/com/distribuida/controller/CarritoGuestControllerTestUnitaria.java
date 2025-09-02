//package com.distribuida.controller;
//
//import com.distribuida.model.Carrito;
//import com.distribuida.service.CarritoService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class CarritoGuestControllerTestUnitaria {
//
//    @InjectMocks
//    private CarritoGuestController carritoGuestController;
//
//    @Mock
//    private CarritoService carritoService;
//
//    private Carrito carrito;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        carrito = new Carrito();
//        carrito.setIdCarrito(1L);
//        carrito.setToken("token123");
//        carrito.setSubtotal(BigDecimal.valueOf(50));
//        carrito.setDescuento(BigDecimal.ZERO);
//        carrito.setImpuestos(BigDecimal.valueOf(6));
//        carrito.setTotal(BigDecimal.valueOf(56));
//        carrito.setActualizadoEn(LocalDateTime.now());
//    }
//
//    @Test
//    public void testCreateOrGetByPacienteId() {
//        when(carritoService.getOrCreateByPacienteId(1, "token123")).thenReturn(carrito);
//
//        ResponseEntity<Carrito> respuesta = carritoGuestController.createOrGet(1, "token123");
//
//        assertEquals(200, respuesta.getStatusCodeValue());
//        assertEquals("token123", respuesta.getBody().getToken());
//        verify(carritoService, times(1)).getOrCreateByPacienteId(1, "token123");
//    }
//
//    @Test
//    public void testGetByPacienteId() {
//        when(carritoService.getByPacienteId(1)).thenReturn(carrito);
//
//        ResponseEntity<Carrito> respuesta = carritoGuestController.get(1);
//
//        assertEquals(200, respuesta.getStatusCodeValue());
//        assertEquals(BigDecimal.valueOf(56), respuesta.getBody().getTotal());
//        verify(carritoService, times(1)).getByPacienteId(1);
//    }
//
//    @Test
//    public void testAddItem() {
//        Map<String, Integer> body = new HashMap<>();
//        body.put("medicamentoId", 10);
//        body.put("cantidad", 2);
//
//        when(carritoService.addItem(1, 10, 2)).thenReturn(carrito);
//
//        ResponseEntity<Carrito> respuesta = carritoGuestController.addItem(1, body);
//
//        assertEquals(200, respuesta.getStatusCodeValue());
//        verify(carritoService, times(1)).addItem(1, 10, 2);
//    }
//
//    @Test
//    public void testUpdateItem() {
//        Map<String, Integer> body = new HashMap<>();
//        body.put("cantidad", 3);
//
//        when(carritoService.updateItemCantidad(1, 5L, 3)).thenReturn(carrito);
//
//        ResponseEntity<Carrito> respuesta = carritoGuestController.update(1, 5L, body);
//
//        assertEquals(200, respuesta.getStatusCodeValue());
//        verify(carritoService, times(1)).updateItemCantidad(1, 5L, 3);
//    }
//
//    @Test
//    public void testRemoveItem() {
//        doNothing().when(carritoService).removeItem(1, 5L);
//
//        ResponseEntity<Void> respuesta = carritoGuestController.remove(1, 5L);
//
//        assertEquals(204, respuesta.getStatusCodeValue());
//        verify(carritoService, times(1)).removeItem(1, 5L);
//    }
//
//    @Test
//    public void testClearCarrito() {
//        doNothing().when(carritoService).clear(1);
//
//        ResponseEntity<Void> respuesta = carritoGuestController.clear(1);
//
//        assertEquals(204, respuesta.getStatusCodeValue());
//        verify(carritoService, times(1)).clear(1);
//    }
//}
