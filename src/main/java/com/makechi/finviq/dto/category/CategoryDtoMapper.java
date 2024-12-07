package com.makechi.finviq.dto.category;

import com.makechi.finviq.collections.Category;
import com.makechi.finviq.dto.user.UserMapper;
import com.makechi.finviq.exception.ResourceNotFoundException;
import com.makechi.finviq.repository.CategoryRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CategoryDtoMapper implements Function<Category, CategoryDto> {

    private final CategoryRepository categoryRepository;
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

    public ModelCategoryDto toModelCategoryDto(@NonNull String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + categoryId + " not found"));

        return new ModelCategoryDto(category.getId(), category.getName());
    }
}
