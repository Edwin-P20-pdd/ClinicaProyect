package com.distribuida.service;

import com.distribuida.dao.CitaRepository;
import com.distribuida.dao.DoctorRepository;
import com.distribuida.dao.PacienteRepository;
import com.distribuida.model.Cita;
import com.distribuida.model.Doctor;
import com.distribuida.model.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<Cita> findAll() {
        return citaRepository.findAll();
    }

    @Override
    public Cita findOne(int id) {
        Optional<Cita> cita = citaRepository.findById(id);
        return cita.orElse(null);
    }

    @Override
    public Cita save(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public Cita update(int id, Cita citaNueva) {
        Cita citaExistente = findOne(id);

//        Optional<Paciente> pacienteExistente = pacienteRepository.findById(idPaciente);
//
//        Optional<Doctor> doctorExistente = doctorRepository.findById(idDoctor);

        if (citaExistente == null) {
            return null;
        }

        citaExistente.setFecha(citaNueva.getFecha());
        citaExistente.setMotivo(citaNueva.getMotivo());
//        citaExistente.setPaciente(pacienteExistente.orElse(null));
//        citaExistente.setDoctor(doctorExistente.orElse(null));

        return citaRepository.save(citaExistente);
    }

    @Override
    public void delete(int id) {
        if (citaRepository.existsById(id)) {
            citaRepository.deleteById(id);
        }
    }
}
