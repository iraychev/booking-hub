package com.iraychev.server.service;

import com.iraychev.model.entities.Image;
import com.iraychev.server.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {


    private final ImageRepository imageRepository;
    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image save(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(UUID.randomUUID().toString() + "_" +file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setData(file.getBytes());
        return imageRepository.save(image);
    }

    public Image getImageById(UUID id) throws Exception {
        return imageRepository.findById(id).orElseThrow(() -> new Exception("Image not found"));
    }
}
