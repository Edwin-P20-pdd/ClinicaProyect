package com.distribuida.service;

import com.distribuida.model.Receta;

import java.util.List;

public interface RecetaService {

    public List<Receta> findAll();

    public Receta findOne(int id);

    public Receta save(Receta receta);

    public Receta update(int id, Receta receta);

    public void delete(int id);
}
