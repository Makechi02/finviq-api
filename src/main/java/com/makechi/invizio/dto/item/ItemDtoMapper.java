package com.makechi.invizio.dto.item;

import com.makechi.invizio.collections.item.Item;
import com.makechi.invizio.dto.category.CategoryDtoMapper;
import com.makechi.invizio.dto.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ItemDtoMapper implements Function<Item, ItemDto> {

    private final UserMapper userMapper;
    private final CategoryDtoMapper categoryDtoMapper;

    @Override
    public ItemDto apply(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .brand(item.getBrand())
                .createdAt(item.getCreatedAt())
                .model(item.getModel())
                .name(item.getName())
                .costPrice(item.getCostPrice())
                .retailPrice(item.getRetailPrice())
                .vatInclusivePrice(item.getVatInclusivePrice())
                .quantity(item.getQuantity())
                .sku(item.getSku())
                .stockAlert(item.getStockAlert())
                .updatedAt(item.getUpdatedAt())
                .category(categoryDtoMapper.toModelCategoryDto(item.getCategory().toHexString()))
                .createdBy(userMapper.toModelUserDto(item.getCreatedBy().toHexString()))
                .updatedBy(userMapper.toModelUserDto(item.getUpdatedBy().toHexString()))
                .build();
    }
}
