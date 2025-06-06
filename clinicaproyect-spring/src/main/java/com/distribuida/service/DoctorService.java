package com.distribuida.service;

import com.distribuida.model.Doctor;

import java.util.List;

public interface DoctorService {

    public List<Doctor> findAll();

    public Doctor findOne(int id);

    public Doctor save(Doctor doctor);

    public Doctor update(int id, Doctor doctor);

    public void delete(int id);

}
