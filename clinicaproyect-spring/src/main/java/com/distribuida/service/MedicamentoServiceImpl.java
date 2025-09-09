package com.distribuida.service;

import com.distribuida.dao.MedicamentoRepository;
import com.distribuida.model.Medicamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Override
    public List<Medicamento> findAll() {
        return medicamentoRepository.findAll();
    }

    @Override
    public Medicamento findOne(int id) {
        Optional<Medicamento> medicamento = medicamentoRepository.findById(id);
        return medicamento.orElse(null);
    }

    @Override
    public Medicamento save(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    @Override
    public Medicamento update(int id, Medicamento medicamentoNuevo) {
        Medicamento medicamentoExistente = findOne(id);

        if (medicamentoExistente == null) {
            return null;
        }

        medicamentoExistente.setNombre(medicamentoNuevo.getNombre());
        medicamentoExistente.setDosis(medicamentoNuevo.getDosis());
        medicamentoExistente.setDescripcion(medicamentoNuevo.getDescripcion());
        medicamentoExistente.setPrecio(medicamentoNuevo.getPrecio());
        medicamentoExistente.setStock(medicamentoNuevo.getStock());
        medicamentoExistente.setPortada(medicamentoNuevo.getPortada());

        return medicamentoRepository.save(medicamentoExistente);
    }

    @Override
    public void delete(int id) {
        if (medicamentoRepository.existsById(id)) {
            medicamentoRepository.deleteById(id);
        }
    }
}
