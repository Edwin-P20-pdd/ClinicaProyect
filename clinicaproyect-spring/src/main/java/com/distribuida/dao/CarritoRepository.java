package com.distribuida.dao;

import com.distribuida.model.Carrito;
import com.distribuida.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByPaciente(Paciente paciente);
    Optional<Carrito> findByToken(String token);
}
