package com.example.homedecor.Service;

import com.example.homedecor.Entity.Furniture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FurnitureService {
    void addFurniture(Furniture furniture, MultipartFile image) throws IOException;
    void deleteById(Long id);
    void updateData(Long id, Furniture furniture, MultipartFile image) throws IOException;
    byte [] getFurnitureImage(Integer furnitureId) throws IOException;
    boolean existsById(Long id);
    Optional<Furniture> findById(Long id);
    List<Furniture> getAll();
    Long furnitureCount();


}
