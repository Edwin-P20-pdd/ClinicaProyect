package com.distribuida.dto;

public class CheckoutRequest {

    private int pacienteId;
    private String metodoPago;

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int clienteId) {
        this.pacienteId = clienteId;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}
