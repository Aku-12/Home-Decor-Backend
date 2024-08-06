package com.example.homedecor;

import com.example.homedecor.Entity.FileData;
import com.example.homedecor.Entity.Furniture;
import com.example.homedecor.Repo.FurnitureRepo;
import com.example.homedecor.Service.Impl.FurnitureServiceImpl;

import com.example.homedecor.Service.Impl.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FurnitureTest {

    @Mock
    private FurnitureRepo furnitureRepo;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private FurnitureServiceImpl furnitureService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddFurniture() throws IOException {
        Furniture furniture = new Furniture();
        MultipartFile image = mock(MultipartFile.class);

        when(image.getContentType()).thenReturn("image/png");
        when(storageService.uploadImageToFileSystem(image)).thenReturn("test.png");

        furnitureService.addFurniture(furniture, image);

        verify(furnitureRepo, times(1)).save(furniture);
        assertNotNull(furniture.getImageData());
        assertEquals("test.png", furniture.getImageData().getName());
        assertEquals("image/png", furniture.getImageData().getType());
    }

    @Test
    public void testDeleteById() {
        Long furnitureId = 1L;

        furnitureService.deleteById(furnitureId);

        verify(furnitureRepo, times(1)).deleteById(furnitureId);
    }

    @Test
    public void testUpdateData() throws IOException {
        Long furnitureId = 1L;
        Furniture furniturePojo = new Furniture();
        furniturePojo.setFurnitureName("New Name");
        furniturePojo.setPrice((long) 100.0);
        furniturePojo.setDetails("New Details");
        MultipartFile image = mock(MultipartFile.class);

        Furniture existingFurniture = new Furniture();
        when(furnitureRepo.findById(furnitureId)).thenReturn(Optional.of(existingFurniture));
        when(image.getContentType()).thenReturn("image/png");
        when(storageService.uploadImageToFileSystem(image)).thenReturn("updated.png");

        furnitureService.updateData(furnitureId, furniturePojo, image);

        verify(furnitureRepo, times(1)).save(existingFurniture);
        assertEquals("New Name", existingFurniture.getFurnitureName());
        assertEquals(100.0, (double) existingFurniture.getPrice()); // Casting to double
        assertEquals("New Details", existingFurniture.getDetails());
        assertNotNull(existingFurniture.getImageData());
        assertEquals("updated.png", existingFurniture.getImageData().getName());
        assertEquals("image/png", existingFurniture.getImageData().getType());
    }

    @Test
    public void testGetFurnitureImage() throws IOException {
        Long furnitureId = 1L;
        Furniture furniture = new Furniture();
        FileData fileData = new FileData();
        fileData.setName("test.png");
        furniture.setImageData(fileData);

        when(furnitureRepo.findById(furnitureId)).thenReturn(Optional.of(furniture));
        when(storageService.downloadImageFromFileSystem("test.png")).thenReturn(new byte[]{1, 2, 3});

        byte[] imageData = furnitureService.getFurnitureImage(furnitureId.intValue());

        assertNotNull(imageData);
        assertEquals(3, imageData.length);
    }

    @Test
    public void testExistsById() {
        Long furnitureId = 1L;
        when(furnitureRepo.existsById(furnitureId)).thenReturn(true);

        boolean exists = furnitureService.existsById(furnitureId);

        assertTrue(exists);
    }

    @Test
    public void testFindById() {
        Long furnitureId = 1L;
        Furniture furniture = new Furniture();
        when(furnitureRepo.findById(furnitureId)).thenReturn(Optional.of(furniture));

        Optional<Furniture> foundFurniture = furnitureService.findById(furnitureId);

        assertTrue(foundFurniture.isPresent());
        assertEquals(furniture, foundFurniture.get());
    }

    @Test
    public void testGetAll() {
        Furniture furniture = new Furniture();
        when(furnitureRepo.findAll()).thenReturn(List.of(furniture));

        List<Furniture> furnitureList = furnitureService.getAll();

        assertNotNull(furnitureList);
        assertFalse(furnitureList.isEmpty());
        assertEquals(1, furnitureList.size());
        assertEquals(furniture, furnitureList.get(0));
    }

    @Test
    public void testFurnitureCount() {
        when(furnitureRepo.count()).thenReturn(10L);

        Long count = furnitureService.furnitureCount();

        assertNotNull(count);
        assertEquals(10L, count);
    }
}
