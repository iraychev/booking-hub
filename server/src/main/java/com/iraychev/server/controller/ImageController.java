package com.iraychev.server.controller;

import com.iraychev.model.entities.Image;
import com.iraychev.server.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("booking-api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<Image> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        Image image = imageService.save(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(image);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(imageService.getImageById(id));
    }
}
