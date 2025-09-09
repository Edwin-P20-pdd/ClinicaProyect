package com.distribuida.service;

import com.distribuida.dao.PacienteRepository;
import com.distribuida.model.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente findOne(int id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        return paciente.orElse(null);
    }

    @Override
    public Paciente save(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente update(int id, Paciente pacienteNuevo) {
        Paciente pacienteExistente = findOne(id);

        if (pacienteExistente == null) {
            return null;
        }

        pacienteExistente.setCedula(pacienteNuevo.getCedula());
        pacienteExistente.setNombre(pacienteNuevo.getNombre());
        pacienteExistente.setApellido(pacienteNuevo.getApellido());
        pacienteExistente.setFechaNacimiento(pacienteNuevo.getFechaNacimiento());
        pacienteExistente.setDireccion(pacienteNuevo.getDireccion());
        pacienteExistente.setTelefono(pacienteNuevo.getTelefono());
        pacienteExistente.setCorreo(pacienteNuevo.getCorreo());

        return pacienteRepository.save(pacienteExistente);
    }

    @Override
    public void delete(int id) {
        if (pacienteRepository.existsById(id)) {
            pacienteRepository.deleteById(id);
        }
    }
}
