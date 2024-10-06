package com.makbe.ims.dto.item;

import com.makbe.ims.dto.category.ModelCategoryDto;
import com.makbe.ims.dto.supplier.ModelSupplierDto;
import com.makbe.ims.dto.user.ModelUserDto;
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
    private ModelSupplierDto supplier;
    private int stockAlert;
    private LocalDateTime updatedAt;
    private ModelUserDto updatedBy;
}
