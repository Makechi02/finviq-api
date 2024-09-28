package com.makbe.ims.dto.metrics;

import com.makbe.ims.dto.category.ModelCategoryDto;
import com.makbe.ims.dto.item.ItemDto;
import com.makbe.ims.dto.supplier.ModelSupplierDto;
import com.makbe.ims.dto.user.ModelUserDto;
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
