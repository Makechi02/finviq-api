package com.makbe.ims.dto.category;

import com.makbe.ims.collections.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDtoMapperTest {

    private CategoryDtoMapper categoryDtoMapper;

    @BeforeEach
    void setUp() {
        categoryDtoMapper = new CategoryDtoMapper();
    }

    @Test
    void shouldMapCategoryToCategoryDto() {
        Category category = Category.builder()
                .id("123")
                .name("electronics")
                .build();

        CategoryDto categoryDto = categoryDtoMapper.apply(category);

        assertNotNull(categoryDto);
        assertEquals("123", categoryDto.getId());
        assertEquals("electronics", categoryDto.getName());
    }


    @Test
    public void shouldThrowNullPointerExceptionWhenCategoryIsNull() {
        var exception = assertThrows(NullPointerException.class, () -> categoryDtoMapper.apply(null));
        assertEquals("Category should not be null", exception.getMessage());
    }

    @Test
    void shouldHandleEmptyFields() {
        Category category = Category.builder()
                .id(null)
                .name(null)
                .build();

        CategoryDto categoryDto = categoryDtoMapper.apply(category);

        assertNotNull(categoryDto);
        assertNull(categoryDto.getId());
        assertNull(categoryDto.getName());
    }

}