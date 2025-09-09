package com.distribuida.service;

import com.distribuida.dao.CitaRepository;
import com.distribuida.dao.RecetaRepository;
import com.distribuida.model.Cita;
import com.distribuida.model.Receta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaServiceImpl implements RecetaService{

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public List<Receta> findAll(){
        return recetaRepository.findAll();
    }

    @Override
    public Receta findOne(int id) {
        Optional<Receta> receta = recetaRepository.findById(id);
        return receta.orElse(null);
    }

    @Override
    public Receta save(Receta receta) {
        return recetaRepository.save(receta);
    }

    @Override
    public Receta update(int id, Receta recetaNueva) {
        Receta recetaExistente = findOne(id);

//        Optional<Cita> citaExistente = citaRepository.findById(idCita);

        if (recetaExistente == null) {
            return null;
        }

        recetaExistente.setDescripcion(recetaNueva.getDescripcion());
        recetaExistente.setFecha(recetaNueva.getFecha());
//        recetaExistente.setCita(citaExistente.orElse(null));

        return recetaRepository.save(recetaExistente);
    }

    @Override
    public void delete(int id) {
        if (recetaRepository.existsById(id)) {
            recetaRepository.deleteById(id);
        }
    }

}
