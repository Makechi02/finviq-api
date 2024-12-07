package com.makechi.finviq.dto.metrics;

import com.makechi.finviq.dto.category.ModelCategoryDto;
import com.makechi.finviq.dto.item.ItemDto;
import com.makechi.finviq.dto.supplier.ModelSupplierDto;
import com.makechi.finviq.dto.user.ModelUserDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MetricsDto {
    private long totalUsers;
    private long totalItems;
    private long totalCategories;
    private long totalSuppliers;
    private List<ModelUserDto> recentUsers;
    private List<ItemDto> recentItems;
    private List<ModelCategoryDto> recentCategories;
    private List<ModelSupplierDto> recentSuppliers;
}
