package com.distribuida.service;

import com.distribuida.dao.DoctorRepository;
import com.distribuida.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Aquí se gestiona la lógica de negocio

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired // inyección de dependencias
    private DoctorRepository doctorRepository;

    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor findOne(int id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.orElse(null);
    }

    @Override
    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor update(int id, Doctor doctorNuevo) {
        Doctor doctorExistente = findOne(id);

        if (doctorExistente == null) {
            return null;
        }

        doctorExistente.setNombre(doctorNuevo.getNombre());
        doctorExistente.setApellido(doctorNuevo.getApellido());
        doctorExistente.setEspecialidad(doctorNuevo.getEspecialidad());
        doctorExistente.setTelefono(doctorNuevo.getTelefono());
        doctorExistente.setCorreo(doctorNuevo.getCorreo());

        return doctorRepository.save(doctorExistente);
    }

    @Override
    public void delete(int id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
        }
    }
}
