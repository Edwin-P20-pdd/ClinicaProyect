package com.distribuida.service;

import com.distribuida.model.RecetaMedicamento;

import java.util.List;

public interface RecetaMedicamentoService {

    public List<RecetaMedicamento> findAll();

    public RecetaMedicamento findOne(int id);

    public RecetaMedicamento save(RecetaMedicamento recetaMedicamento);

    public RecetaMedicamento update(int id,  RecetaMedicamento recetaMedicamento);

    public void delete(int id);
}
