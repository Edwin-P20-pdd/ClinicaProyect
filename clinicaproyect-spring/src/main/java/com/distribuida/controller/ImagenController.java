package com.distribuida.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ImagenController {

    private static final String UPLOAD_DIR = "uploads/portadas/";

    @PostMapping("/upload-portada")
    public ResponseEntity<Map<String, String>> uploadPortada(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "oldImage", required = false) String oldImage
    ) {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // Guardar con nombre limpio: nombre original
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                originalFilename = "imagen.jpg";
            }

            Path path = Paths.get(UPLOAD_DIR + originalFilename);
            Files.write(path, file.getBytes());

            // Borrar imagen vieja si existe
            if (oldImage != null && !oldImage.isEmpty()) {
                Path oldImagePath = Paths.get(UPLOAD_DIR + oldImage);
                Files.deleteIfExists(oldImagePath);
            }

            Map<String, String> response = new HashMap<>();
            response.put("ruta", "portadas/" + originalFilename);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al subir imagen: " + e.getMessage()));
        }
    }
}
