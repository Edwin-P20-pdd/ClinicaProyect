package com.distribuida.controller;

import com.distribuida.model.Paciente;
import com.distribuida.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> findAll() {
        List<Paciente> pacientes = pacienteService.findAll();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> findOne(@PathVariable int id) {
        Paciente paciente = pacienteService.findOne(id);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paciente);
    }

    @PostMapping
    public ResponseEntity<Paciente> save(@RequestBody Paciente paciente) {
        Paciente pacienteNuevo = pacienteService.save(paciente);
        return ResponseEntity.ok(pacienteNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> update(@PathVariable int id, @RequestBody Paciente paciente) {
        Paciente pacienteActualizado = pacienteService.update(id, paciente);
        if (pacienteActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pacienteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
