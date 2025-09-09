package com.distribuida.service;

import com.distribuida.model.Cita;

import java.util.List;

public interface CitaService {

    public List<Cita> findAll();

    public Cita findOne(int id);

    public Cita save(Cita cita);

    public Cita update(int id, Cita cita);

    public void delete(int id);
}
