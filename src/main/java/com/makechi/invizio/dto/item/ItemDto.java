package com.makechi.invizio.dto.item;

import com.makechi.invizio.dto.category.ModelCategoryDto;
import com.makechi.invizio.dto.user.ModelUserDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ItemDto {
    private String id;
    private String brand;
    private ModelCategoryDto category;
    private LocalDateTime createdAt;
    private ModelUserDto createdBy;
    private String model;
    private String name;
    private double price;
    private double quantity;
    private String sku;
    private int stockAlert;
    private LocalDateTime updatedAt;
    private ModelUserDto updatedBy;
}
