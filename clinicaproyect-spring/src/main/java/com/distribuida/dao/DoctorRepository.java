package com.distribuida.dao;

import com.distribuida.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Doctor findByNombre(String nombre);
}
