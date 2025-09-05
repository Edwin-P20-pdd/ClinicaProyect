package com.distribuida.controller;

import com.distribuida.model.Cita;
import com.distribuida.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping
    public ResponseEntity<List<Cita>> findAll() {
        List<Cita> citas = citaService.findAll();
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> findOne(@PathVariable int id) {
        Cita cita = citaService.findOne(id);
        if (cita == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cita);
    }

    @PostMapping
    public ResponseEntity<Cita> save(@RequestBody Cita cita) {
        Cita nuevaCita = citaService.save(cita);
        return ResponseEntity.ok(nuevaCita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> update(
            @PathVariable int id,
            @RequestBody Cita cita) {

        Cita citaActualizada = citaService.update(id, cita);
        if (citaActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(citaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        citaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
