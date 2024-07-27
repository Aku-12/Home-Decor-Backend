package com.example.homedecor.Pojo;

import com.example.homedecor.Entity.FileData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FurniturePojo {
    private Long id;
    private String furnitureName;
    private String details;
    private Long price;
    private FileData imageData;
}
