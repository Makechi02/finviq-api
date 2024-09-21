package com.makbe.ims.dto.category;

import com.makbe.ims.collections.Category;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CategoryDtoMapper implements Function<Category, CategoryDto> {
    @Override
    public CategoryDto apply(Category category) {
        if (category == null)
            throw new NullPointerException("Category should not be null");

        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
