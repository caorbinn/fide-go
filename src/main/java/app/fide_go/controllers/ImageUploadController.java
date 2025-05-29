package app.fide_go.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/api/images")
public class ImageUploadController {

    private final String uploadDir = "uploads/images/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Asegura que exista la carpeta
            File directory = new File(uploadDir);
            if (!directory.exists()) directory.mkdirs();

            // Guarda el archivo con su nombre original
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path filepath = Paths.get(uploadDir, filename);
            Files.write(filepath, file.getBytes());

            // Devuelve la URL relativa (ajustar si usas dominio público)
            String fileUrl = "/uploads/images/" + filename;
            return ResponseEntity.ok(fileUrl);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al subir imagen: " + e.getMessage());
        }
    }
}
