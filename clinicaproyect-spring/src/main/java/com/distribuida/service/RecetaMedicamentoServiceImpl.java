package com.distribuida.service;

import com.distribuida.dao.MedicamentoRepository;
import com.distribuida.dao.RecetaMedicamentoRepository;
import com.distribuida.dao.RecetaRepository;
import com.distribuida.model.Medicamento;
import com.distribuida.model.Receta;
import com.distribuida.model.RecetaMedicamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaMedicamentoServiceImpl implements RecetaMedicamentoService{

    @Autowired
    private RecetaMedicamentoRepository recetaMedicamentoRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Override
    public List<RecetaMedicamento> findAll() {
        return recetaMedicamentoRepository.findAll();
    }

    @Override
    public RecetaMedicamento findOne(int id) {
        Optional<RecetaMedicamento> recetaMedicamento = recetaMedicamentoRepository.findById(id);
        return recetaMedicamento.orElse(null);
    }

    @Override
    public RecetaMedicamento save(RecetaMedicamento recetaMedicamento) {
        return recetaMedicamentoRepository.save(recetaMedicamento);
    }

    @Override
    public RecetaMedicamento update(int id, RecetaMedicamento recetaMedicamentoNueva) {
        RecetaMedicamento recetaMedicamentoexistente = findOne(id);
//        Optional<Receta> recetaExistente = recetaRepository.findById(idReceta);
//        Optional<Medicamento> medicamentoExistente = medicamentoRepository.findById(idMedicamento);

        if (recetaMedicamentoexistente == null) {
            return null;
        }

//        recetaMedicamentoexistente.setReceta(recetaExistente.orElse(null));
//        recetaMedicamentoexistente.setMedicamento(medicamentoExistente.orElse(null));

        return recetaMedicamentoRepository.save(recetaMedicamentoexistente);
    }

    @Override
    public void delete(int id) {
        if (recetaMedicamentoRepository.existsById(id)) {
            recetaMedicamentoRepository.deleteById(id);
        }
    }

}
