package com.distribuida.service;

import com.distribuida.dao.CarritoItemRepository;
import com.distribuida.dao.CarritoRepository;
import com.distribuida.dao.PacienteRepository;
import com.distribuida.dao.MedicamentoRepository;
import com.distribuida.model.Carrito;
import com.distribuida.model.CarritoItem;
import jakarta.transaction.Transactional;
import org.hibernate.id.enhanced.InitialValueAwareOptimizer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class CarritoServiceImpl implements CarritoService{


    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository carritoItemRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicamentoRepository medicamentoRepository;

    private static final BigDecimal IVA = new BigDecimal("0.15");

    public CarritoServiceImpl(CarritoRepository carritoRepository
            , CarritoItemRepository carritoItemRepository
            , PacienteRepository pacienteRepository
            , MedicamentoRepository medicamentoRepository
    ){

        this.carritoRepository = carritoRepository;
        this.carritoItemRepository = carritoItemRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicamentoRepository = medicamentoRepository;

    }


    @Override
    @Transactional
    public Carrito getOrCreateByPacienteId(int pacienteId, String token) {
        var paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"+ pacienteId));

        var carritoOpt = carritoRepository.findByPaciente(paciente);
        if(carritoOpt.isPresent()) return carritoOpt.get();

        var carrito = new Carrito();
        carrito.setPaciente(paciente);
        carrito.setToken(token);
        carrito.recomprobacionTotalesCompat();

        return carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public Carrito addItem(int pacienteId, int medicamentoId, int cantidad) {

        if(cantidad <= 0 ) throw new IllegalArgumentException("Cantidad debe ser > 0");

        var carrito = getOrCreateByPacienteId(pacienteId, null);
        var medicamento = medicamentoRepository.findById(medicamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Medicamento no encontrado: " + medicamentoId));

        var itemOpt = carritoItemRepository.findByCarritoAndMedicamento(carrito, medicamento);
        if(itemOpt.isPresent()){
            var item =itemOpt.get();
            item.setCantidad(item.getCantidad() + cantidad);
            item.setPrecioUnitario(BigDecimal.valueOf(medicamento.getPrecio()));
            item.calcTotal();
            carritoItemRepository.save(item);
        } else{
            var item = new CarritoItem();
            item.setCarrito(carrito);
            item.setMedicamento(medicamento);
            item.setCantidad(cantidad);
            item.setPrecioUnitario(BigDecimal.valueOf(medicamento.getPrecio()));
            item.calcTotal();
            carrito.getItems().add(item);
        }
        carrito.recomputarTotales(IVA);
        return carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public Carrito updateItemCantidad(int pacienteId, long carritoItemId, int nuevaCantidad) {
        if(nuevaCantidad < 0) throw new IllegalArgumentException("Cantidad no puede ser negativa");

        var carrito = getByPacienteId(pacienteId);
        var item = carritoItemRepository.findById(carritoItemId)
                .orElseThrow(() -> new IllegalArgumentException("Item no encontrado"+carritoItemId));
        if(!item.getCarrito().getIdCarrito().equals(carrito.getIdCarrito())){
            throw  new IllegalArgumentException("El item no pertenece al carrito del paciente");
        }
        if(nuevaCantidad == 0){
            carrito.getItems().remove(item);
            carritoItemRepository.delete(item);
        }else{
            item.setCantidad(nuevaCantidad);
            carritoItemRepository.save(item);
        }
        carrito.recomputarTotales(IVA);
        return carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public void removeItem(int pacienteId, long carritoItemId) {
        updateItemCantidad(pacienteId, carritoItemId,0);
    }

    @Override
    @Transactional
    public void clear(int pacienteId) {
        var carrito = getByPacienteId(pacienteId);
        carrito.getItems().clear();
        carrito.recomputarTotales(IVA);
        carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public Carrito getByPacienteId(int pacienteId) {
        var paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"+ pacienteId));
        return carritoRepository.findByPaciente(paciente)
                .orElseGet(() -> {
                    var c = new Carrito();
                    c.setPaciente(paciente);
                    return c;
                });
    }

    @Override
    @Transactional
    public Carrito getOrCreateByToken(String token) {
        if(token == null || token.isBlank())
            throw new IllegalArgumentException("Token de carrito requerido");
        return carritoRepository.findByToken(token).orElseGet(() ->{
            var c = new Carrito();
            c.setToken(token);
            c.setSubtotal(BigDecimal.ZERO);
            c.setDescuento(BigDecimal.ZERO);
            c.setImpuestos(BigDecimal.ZERO);
            c.setTotal(BigDecimal.ZERO);
            return carritoRepository.save(c);
        });
    }

    @Override
    @Transactional
    public Carrito addItem(String token, int medicamentoId, int cantidad) {
        if(cantidad <= 0) throw  new IllegalArgumentException("Cantidad debe ser > 0");
        var carrito = getOrCreateByToken(token);
        var medicamento = medicamentoRepository.findById(medicamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Medicamento no encontrado: " + medicamentoId));
        var itemOpt = carritoItemRepository.findByCarritoAndMedicamento(carrito, medicamento);
        if(itemOpt.isPresent()){
            var item = itemOpt.get();
            item.setCantidad(item.getCantidad() + cantidad);
            item.setPrecioUnitario(BigDecimal.valueOf(medicamento.getPrecio()));
            item.calcTotal();
            carritoItemRepository.save(item);
        }else{
            var item = new CarritoItem();
            item.setCarrito(carrito);
            item.setMedicamento(medicamento);
            item.setCantidad(cantidad);
            item.setPrecioUnitario(BigDecimal.valueOf(medicamento.getPrecio()));
            item.calcTotal();
            carrito.getItems().add(item);
        }
        carrito.recomputarTotales(IVA);
        return carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public Carrito updateItemCantidad(String token, long carritoItemId, int nuevaCantidad) {
        var carrito = getOrCreateByToken(token);
        var item = carritoItemRepository.findById(carritoItemId)
                .orElseThrow(() -> new IllegalArgumentException("Item no encontrado: "+ carritoItemId));

        if(nuevaCantidad <= 0){
            carrito.getItems().remove(item);
            carritoItemRepository.delete(item);
        }else{
            item.setCantidad(nuevaCantidad);
            item.calcTotal();
            carritoItemRepository.save(item);
        }
        carrito.recomputarTotales(IVA);
        return carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public void removeItem(String token, long carritoItemId) {
        updateItemCantidad(token, carritoItemId, 0);
    }

    @Override
    @Transactional
    public void clearByToken(String token) {
        var carrito = getOrCreateByToken(token);
        carrito.getItems().clear();
        carrito.setSubtotal(BigDecimal.ZERO);
        carrito.setDescuento(BigDecimal.ZERO);
        carrito.setImpuestos(BigDecimal.ZERO);
        carrito.setTotal(BigDecimal.ZERO);
        carritoRepository.save(carrito);
    }

    @Override
    @Transactional
    public Carrito getByToken(String token) {
        return carritoRepository.findByToken(token)
                .orElseGet(()->{
                    var c = new Carrito();
                    c.setToken(token);
                    c.setSubtotal(BigDecimal.ZERO);
                    c.setDescuento(BigDecimal.ZERO);
                    c.setImpuestos(BigDecimal.ZERO);
                    c.setTotal(BigDecimal.ZERO);
                    return c;
                });
    }
}
