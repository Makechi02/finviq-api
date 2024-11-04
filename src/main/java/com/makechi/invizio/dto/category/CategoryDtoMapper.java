package com.makechi.invizio.dto.category;

import com.makechi.invizio.collections.Category;
import com.makechi.invizio.dto.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CategoryDtoMapper implements Function<Category, CategoryDto> {

    private final UserMapper userMapper;

    @Override
    public CategoryDto apply(Category category) {
        if (category == null)
            throw new NullPointerException("Category should not be null");

        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .createdBy(userMapper.toModelUserDto(category.getCreatedBy().toHexString()))
                .createdAt(category.getCreatedAt())
                .updatedBy(userMapper.toModelUserDto(category.getUpdatedBy().toHexString()))
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    public ModelCategoryDto toModelCategoryDto(Category category) {
        if (category == null)
            throw new NullPointerException("Category should not be null");

        return new ModelCategoryDto(category.getId(), category.getName());
    }
}
