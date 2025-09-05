package com.distribuida.service;


import com.distribuida.dao.CarritoRepository;
import com.distribuida.dao.FacturaDetalleRepository;
import com.distribuida.dao.FacturaRepository;
import com.distribuida.dao.MedicamentoRepository;
import com.distribuida.model.Factura;
import com.distribuida.service.util.CheckoutMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class GuestCheckoutServiceImpl implements GuestCheckoutService{

    private final CarritoRepository carritoRepository;
    private final FacturaRepository facturaRepository;
    private final FacturaDetalleRepository facturaDetalleRepository;
    private final MedicamentoRepository medicamentoRepository;

    private static final double IVA = 0.15d;

    public GuestCheckoutServiceImpl(
            CarritoRepository carritoRepository,
            FacturaRepository facturaRepository,
            FacturaDetalleRepository facturaDetalleRepository,
            MedicamentoRepository medicamentoRepository
    ){
        this.carritoRepository = carritoRepository;
        this.facturaRepository = facturaRepository;
        this.facturaDetalleRepository = facturaDetalleRepository;
        this.medicamentoRepository = medicamentoRepository;

    }

    @Override
    @Transactional
    public Factura checkoutByToken(String token) {
        var carrito = carritoRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("No existe carrito para el token"));

        if(carrito.getItems() == null || carrito.getItems().isEmpty()){
            throw new IllegalArgumentException("El carrito está vacio");
        }
        for(var item: carrito.getItems()){
            var medicamento = item.getMedicamento();
            if(medicamento.getStock() < item.getCantidad()){
                throw  new IllegalArgumentException("Stock  insuficiente para: "+ medicamento.getNombre());
            }
        }

        for(var item: carrito.getItems()){
            var medicamento = item.getMedicamento();
            medicamento.setStock(medicamento.getStock() - item.getCantidad());
            medicamentoRepository.save(medicamento);
        }

        String numFactura = "F-"+ DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                .format(LocalDateTime.now());

        var factura = CheckoutMapper.construirFacturaDesdeCarrito(carrito, numFactura, IVA);

        factura = facturaRepository.save(factura);
        for(var item: carrito.getItems()){
            var det =CheckoutMapper.construirDetalle(factura, item);
            facturaDetalleRepository.save(det);
        }

        carrito.getItems().clear();
        carritoRepository.save(carrito);

        return factura;
    }
}
