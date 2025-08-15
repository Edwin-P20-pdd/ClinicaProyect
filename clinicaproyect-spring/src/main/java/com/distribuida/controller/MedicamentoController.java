package com.distribuida.controller;

import com.distribuida.model.Medicamento;
import com.distribuida.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping
    public ResponseEntity<List<Medicamento>> findAll() {
        List<Medicamento> medicamentos = medicamentoService.findAll();
        return ResponseEntity.ok(medicamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> findOne(@PathVariable int id) {
        Medicamento medicamento = medicamentoService.findOne(id);
        if (medicamento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(medicamento);
    }

    @PostMapping
    public ResponseEntity<Medicamento> save(@RequestBody Medicamento medicamento) {
        Medicamento nuevoMedicamento = medicamentoService.save(medicamento);
        return ResponseEntity.ok(nuevoMedicamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> update(@PathVariable int id, @RequestBody Medicamento medicamento) {
        Medicamento medicamentoActualizado = medicamentoService.update(id, medicamento);
        if (medicamentoActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(medicamentoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        medicamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
