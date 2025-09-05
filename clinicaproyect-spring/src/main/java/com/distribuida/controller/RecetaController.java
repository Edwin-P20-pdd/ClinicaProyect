package com.distribuida.controller;

import com.distribuida.model.Receta;
import com.distribuida.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recetas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @GetMapping
    public ResponseEntity<List<Receta>> findAll() {
        List<Receta> recetas = recetaService.findAll();
        return ResponseEntity.ok(recetas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receta> findOne(@PathVariable int id) {
        Receta receta = recetaService.findOne(id);
        if (receta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(receta);
    }

    @PostMapping
    public ResponseEntity<Receta> save(@RequestBody Receta receta) {
        Receta nuevaReceta = recetaService.save(receta);
        return ResponseEntity.ok(nuevaReceta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receta> update(
            @PathVariable int id,
            @RequestBody Receta receta) {

        Receta recetaActualizada = recetaService.update(id, receta);
        if (recetaActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recetaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        recetaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
