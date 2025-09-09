package com.distribuida.service;

import com.distribuida.model.Medicamento;

import java.util.List;

public interface MedicamentoService {

    public List<Medicamento> findAll();

    public Medicamento findOne(int id);

    public Medicamento save(Medicamento medicamento);

    public Medicamento update(int id, Medicamento medicamento);

    public void delete(int id);


}
