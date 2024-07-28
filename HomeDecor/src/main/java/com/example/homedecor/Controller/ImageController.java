package com.example.homedecor.Controller;


import com.example.homedecor.Service.Impl.StorageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);


    private final StorageService storageService;


    @PostMapping("/fileSystem")
    public ResponseEntity<?> uploadImageToFileSystem(@RequestParam("images") MultipartFile file) {
        logger.info("Received request to upload image: {}", file.getOriginalFilename());
        try {
            String uploadImage = storageService.uploadImageToFileSystem(file);
            logger.info("Image uploaded successfully: {}", file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
        } catch (Exception e) {
            logger.error("Image upload failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed: " + e.getMessage());
        }
    }
    //
    @GetMapping("fileSystem/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        logger.info("Received request to download image: {}", fileName);
        byte[] imageData = storageService.downloadImageFromFileSystem(fileName);
        if (imageData != null) {
            logger.info("Image found: {}", fileName);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(imageData);
        } else {
            logger.warn("Image not found: {}", fileName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
        }
    }
}
