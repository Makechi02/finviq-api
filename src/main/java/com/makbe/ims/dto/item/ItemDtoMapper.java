package com.makbe.ims.dto.item;

import com.makbe.ims.collections.Item;
import com.makbe.ims.dto.category.CategoryDtoMapper;
import com.makbe.ims.dto.supplier.SupplierDtoMapper;
import com.makbe.ims.dto.user.UserMapper;
import com.makbe.ims.repository.UserRepository;
import com.makbe.ims.service.category.CategoryService;
import com.makbe.ims.service.supplier.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ItemDtoMapper implements Function<Item, ItemDto> {

    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final SupplierService supplierService;
    private final CategoryDtoMapper categoryDtoMapper;
    private final SupplierDtoMapper supplierDtoMapper;
    private final UserMapper userMapper;

    @Override
    public ItemDto apply(Item item) {
        var createdBy = userMapper.toItemUserDto(userRepository.findById(item.getCreatedBy()).orElseThrow());
        var updatedBy = userMapper.toItemUserDto(userRepository.findById(item.getUpdatedBy()).orElseThrow());
        var supplier = supplierDtoMapper.apply(supplierService.getSupplierById(item.getSupplier()));
        var category = categoryDtoMapper.apply(categoryService.getCategoryById(item.getCategory()));

        return ItemDto.builder()
                .id(item.getId())
                .brand(item.getBrand())
                .createdAt(item.getCreatedAt())
                .model(item.getModel())
                .name(item.getName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .sku(item.getSku())
                .stockAlert(item.getStockAlert())
                .updatedAt(item.getUpdatedAt())
                .category(category)
                .createdBy(createdBy)
                .updatedBy(updatedBy)
                .supplier(supplier)
                .build();
    }
}
