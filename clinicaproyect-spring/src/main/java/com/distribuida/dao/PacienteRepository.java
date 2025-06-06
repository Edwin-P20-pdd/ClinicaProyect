package com.distribuida.dao;

import com.distribuida.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    Paciente findByCedula(String cedula);

}
