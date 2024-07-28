package com.example.homedecor.Controller;


import com.example.homedecor.Entity.Furniture;
import com.example.homedecor.Service.FurnitureService;
import com.example.homedecor.shared.GlobalApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/furniture")
@RequiredArgsConstructor
public class FurnitureController {

    private final FurnitureService furnitureService;

    @GetMapping("/get")
    public GlobalApiResponse<List<Furniture>> getData() {
        List<Furniture> furniture = furnitureService.getAll();
        return GlobalApiResponse.<List<Furniture>>builder()
                .data(furniture)
                .statusCode(200)
                .message("Data retrieved successfully!")
                .build();
    }

    @PostMapping(value="/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GlobalApiResponse<Furniture> createDestination(@RequestPart("furniture")Furniture furniture,
                                                             @RequestPart("image") MultipartFile image) {
        try{
            furnitureService.addFurniture(furniture,image);
            return GlobalApiResponse.<Furniture>builder()
                    .data(furniture)
                    .statusCode(201)
                    .message("Home created successfully!")
                    .build();
        } catch (IOException e) {
            return GlobalApiResponse.<Furniture>builder()
                    .statusCode(500)
                    .message("Failed to process image!")
                    .build();
        }
    }

    @GetMapping("/get/{id}")
    public GlobalApiResponse<Furniture> getData(@PathVariable Long id) {
        Optional<Furniture> furniture = furnitureService.findById(id);
        if (furniture.isPresent()) {
            return GlobalApiResponse.<Furniture>builder()
                    .data(furniture.get())
                    .statusCode(200)
                    .message("Building retrieved successfully!")
                    .build();
        } else {
            return GlobalApiResponse.<Furniture>builder()
                    .statusCode(404)
                    .message("Building not found!")
                    .build();
        }
    }
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GlobalApiResponse<Furniture> updateData(@PathVariable Integer id,
                                                      @RequestPart("furniture") Furniture furnitureDetails,
                                                      @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            furnitureService.updateData(Long.valueOf(id), furnitureDetails, image);
            Furniture updatedHome = furnitureService.findById(Long.valueOf(id)).orElse(null);
            return GlobalApiResponse.<Furniture>builder()
                    .data(updatedHome)
                    .statusCode(200)
                    .message("Destination updated successfully!")
                    .build();
        } catch (IOException e) {
            return GlobalApiResponse.<Furniture>builder()
                    .statusCode(500)
                    .message("Failed to process image!")
                    .build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public GlobalApiResponse<Void> delete(@PathVariable Long id) {
        if (!furnitureService.existsById(id)) {
            return GlobalApiResponse.<Void>builder()
                    .statusCode(404)
                    .message("Ground with ID " + id + " not found")
                    .build();
        }

        furnitureService.deleteById(id);

        return GlobalApiResponse.<Void>builder()
                .statusCode(200)
                .message("furniture with ID " + id + " deleted successfully")
                .build();
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getHomeImage(@PathVariable Integer id) {
        try {
            byte[] imageData = furnitureService.getFurnitureImage(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/count")
    public GlobalApiResponse<Long> count() {
        return GlobalApiResponse.<Long>builder()
                .data(furnitureService.furnitureCount())
                .statusCode(200)
                .message("Data retrieved successfully!")
                .build();
    }
}
