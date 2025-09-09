package com.distribuida.controller;

import com.distribuida.model.RecetaMedicamento;
import com.distribuida.service.RecetaMedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receta-medicamentos")
public class RecetaMedicamentoController {

    @Autowired
    private RecetaMedicamentoService recetaMedicamentoService;

    @GetMapping
    public ResponseEntity<List<RecetaMedicamento>> findAll() {
        List<RecetaMedicamento> lista = recetaMedicamentoService.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaMedicamento> findOne(@PathVariable int id) {
        RecetaMedicamento recetaMedicamento = recetaMedicamentoService.findOne(id);
        if (recetaMedicamento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recetaMedicamento);
    }

    @PostMapping
    public ResponseEntity<RecetaMedicamento> save(@RequestBody RecetaMedicamento recetaMedicamento) {
        RecetaMedicamento nueva = recetaMedicamentoService.save(recetaMedicamento);
        return ResponseEntity.ok(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaMedicamento> update(
            @PathVariable int id,
            @RequestBody RecetaMedicamento recetaMedicamento) {

        RecetaMedicamento actualizada = recetaMedicamentoService.update(id, recetaMedicamento);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        recetaMedicamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
