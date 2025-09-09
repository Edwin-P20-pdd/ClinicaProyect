package com.distribuida.dao;

import com.distribuida.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {

    Medicamento findByNombre(String nombre);


}
