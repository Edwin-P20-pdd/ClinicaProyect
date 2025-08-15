package com.distribuida.service;

import com.distribuida.model.Paciente;

import java.util.List;

public interface PacienteService {

    public List<Paciente> findAll();

    public Paciente findOne(int id);

    public Paciente save(Paciente paciente);

    public Paciente update(int id, Paciente paciente);

    public void delete(int id);
}
