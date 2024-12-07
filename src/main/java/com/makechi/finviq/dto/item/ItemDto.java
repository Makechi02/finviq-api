package com.makechi.finviq.dto.item;

import com.makechi.finviq.dto.category.ModelCategoryDto;
import com.makechi.finviq.dto.user.ModelUserDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ItemDto {
    private String id;
    private String brand;
    private ModelCategoryDto category;
    private BigDecimal costPrice;
    private LocalDateTime createdAt;
    private ModelUserDto createdBy;
    private String model;
    private String name;
    private double quantity;
    private BigDecimal retailPrice;
    private String sku;
    private int stockAlert;
    private LocalDateTime updatedAt;
    private ModelUserDto updatedBy;
    private BigDecimal vatInclusivePrice;
}
