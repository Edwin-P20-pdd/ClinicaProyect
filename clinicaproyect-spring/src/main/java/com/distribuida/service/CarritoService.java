package com.distribuida.service;

import com.distribuida.model.Carrito;

public interface CarritoService {

    Carrito getOrCreateByPacienteId(int pacienteId, String token);
    Carrito addItem(int pacienteId, int medicamentoId, int cantidad);
    Carrito updateItemCantidad(int pacienteId, long carritoItemId, int nuevaCantidad);
    void removeItem(int pacienteId, long carritoItemId);
    void clear(int pacienteId);
    Carrito getByPacienteId(int pacienteId);

    Carrito getOrCreateByToken(String token);
    Carrito addItem(String token, int medicamentoId, int cantidad);
    Carrito updateItemCantidad(String token, long carritoItemId, int nuevaCantidad);
    void removeItem(String token, long carritoItemId);
    void clearByToken(String token);
    Carrito getByToken(String token);



}
