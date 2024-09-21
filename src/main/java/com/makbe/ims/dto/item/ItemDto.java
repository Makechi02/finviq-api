package com.makbe.ims.dto.item;

import com.makbe.ims.dto.category.CategoryDto;
import com.makbe.ims.dto.supplier.SupplierDto;
import com.makbe.ims.dto.user.ItemUserDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ItemDto {
    private String id;
    private String brand;
    private CategoryDto category;
    private LocalDateTime createdAt;
    private ItemUserDto createdBy;
    private String model;
    private String name;
    private double price;
    private double quantity;
    private String sku;
    private SupplierDto supplier;
    private int stockAlert;
    private LocalDateTime updatedAt;
    private ItemUserDto updatedBy;
}
