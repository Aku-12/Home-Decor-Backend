package com.example.homedecor.Service.Impl;

import com.example.homedecor.Entity.FileData;
import com.example.homedecor.Entity.Furniture;
import com.example.homedecor.Repo.FurnitureRepo;
import com.example.homedecor.Service.FurnitureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FurnitureServiceImpl implements FurnitureService {
    private final FurnitureRepo furnitureRepo;
    private final StorageService storageService;
    @Override
    public void addFurniture(Furniture furniture, MultipartFile image) throws IOException {
        String fileName = storageService.uploadImageToFileSystem(image);
        FileData imageData = FileData.builder()
                .name(fileName)
                .type(image.getContentType())
                .filePath(storageService.FOLDER_PATH + fileName)
                .build();
        furniture.setImageData(imageData);

        furnitureRepo.save(furniture);
    }

    @Override
    public void deleteById(Long id) {
     furnitureRepo.deleteById(id);
    }

    @Override
    public void updateData(Long id, Furniture furniturePojo, MultipartFile image) throws IOException {
        Optional<Furniture> furnitureOptional = furnitureRepo.findById(id);
        if (furnitureOptional.isPresent()) {
            Furniture furniture = furnitureOptional.get();
            furniture.setFurnitureName(furniturePojo.getFurnitureName());
            furniture.setPrice(furniturePojo.getPrice());
            furniture.setDetails(furniturePojo.getDetails());

            if (image != null && !image.isEmpty()) {
                String fileName = storageService.uploadImageToFileSystem(image);
                FileData imageData = FileData.builder()
                        .name(fileName)
                        .type(image.getContentType())
                        .filePath(storageService.FOLDER_PATH + fileName)
                        .build();
                furniture.setImageData(imageData);
            }
            furnitureRepo.save(furniture);
        }
    }

    @Override
    public byte[] getFurnitureImage(Integer furnitureId) throws IOException {
        System.out.println("Fetching image for destination ID: " + furnitureId);
        Optional<Furniture> optionalDestination = furnitureRepo.findById(furnitureId.longValue());
        if (optionalDestination.isPresent() && optionalDestination.get().getImageData() != null) {
            String fileName = optionalDestination.get().getImageData().getName();
            if (fileName != null) {
                System.out.println("Found image file name: " + fileName);
                byte[] imageData = storageService.downloadImageFromFileSystem(fileName);
                if (imageData == null) {
                    System.out.println("Failed to retrieve image data for file: " + fileName);
                }
                return imageData;
            } else {
                System.out.println("Image file name is null for furniture ID: " + furnitureId);
            }
        } else {
            System.out.println("No image data found for furniture ID: " + furnitureId);
        }
        return null;
    }


    @Override
    public boolean existsById(Long id) {
        return furnitureRepo.existsById(id);
    }

    @Override
    public Optional<Furniture> findById(Long id) {
        return furnitureRepo.findById(id);
    }

    @Override
    public List<Furniture> getAll() {
       return furnitureRepo.findAll();
    }

    @Override
    public Long furnitureCount() {
        return furnitureRepo.count();
    }
}
