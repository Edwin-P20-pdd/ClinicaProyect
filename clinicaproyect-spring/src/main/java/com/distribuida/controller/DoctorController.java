package com.distribuida.controller;

import com.distribuida.model.Doctor;
import com.distribuida.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctores")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    //
    @GetMapping
    public ResponseEntity<List<Doctor>> findAll() {
        List<Doctor> doctores = doctorService.findAll();
        return ResponseEntity.ok(doctores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> findOne(@PathVariable int id) {
        Doctor doctor = doctorService.findOne(id);
        if (doctor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctor);
    }

    @PostMapping
    public ResponseEntity<Doctor> save(@RequestBody Doctor doctor) {
        Doctor doctorNuevo = doctorService.save(doctor);
        return ResponseEntity.ok(doctorNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> update(@PathVariable int id, @RequestBody Doctor doctor) {
        Doctor doctorActualizado = doctorService.update(id, doctor);
        if (doctorActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctorActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
