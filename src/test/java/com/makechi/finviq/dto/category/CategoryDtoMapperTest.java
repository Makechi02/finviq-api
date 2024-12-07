package com.makechi.finviq.dto.category;

import com.makechi.finviq.collections.Category;
import com.makechi.finviq.dto.user.ModelUserDto;
import com.makechi.finviq.dto.user.UserMapper;
import com.makechi.finviq.exception.ResourceNotFoundException;
import com.makechi.finviq.repository.CategoryRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryDtoMapperTest {

    private CategoryDtoMapper categoryDtoMapper;
    private CategoryRepository categoryRepository;
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = mock(UserMapper.class);
        categoryRepository = mock(CategoryRepository.class);
        categoryDtoMapper = new CategoryDtoMapper(categoryRepository, userMapper);
    }

    @Test
    void shouldMapCategoryToCategoryDto() {
        Category category = Category.builder()
                .id("123")
                .name("electronics")
                .createdBy(new ObjectId("66d0a17eb48aebab27f74eb6"))
                .updatedBy(new ObjectId("66d0a17eb48aebab27f74eb6"))
                .build();

        var modelUserDto = new ModelUserDto("66d0a17eb48aebab27f74eb6", "Makechi Eric");
        when(userMapper.toModelUserDto("66d0a17eb48aebab27f74eb6")).thenReturn(modelUserDto);

        CategoryDto categoryDto = categoryDtoMapper.apply(category);

        assertNotNull(categoryDto);
        assertEquals("123", categoryDto.getId());
        assertEquals("electronics", categoryDto.getName());
        assertEquals("66d0a17eb48aebab27f74eb6", categoryDto.getCreatedBy().id());
        assertEquals("66d0a17eb48aebab27f74eb6", categoryDto.getUpdatedBy().id());
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
                .createdBy(new ObjectId("66d0a17eb48aebab27f74eb6"))
                .updatedBy(new ObjectId("66d0a17eb48aebab27f74eb6"))
                .build();

        when(userMapper.toModelUserDto("66d0a17eb48aebab27f74eb6")).thenReturn(Mockito.mock(ModelUserDto.class));

        CategoryDto categoryDto = categoryDtoMapper.apply(category);

        assertNotNull(categoryDto);
        assertNull(categoryDto.getId());
        assertNull(categoryDto.getName());
    }

    @Test
    void shouldMapCategoryToModelCategoryDto() {
        Category category = Category.builder()
                .id("123")
                .name("electronics")
                .createdBy(new ObjectId("66d0a17eb48aebab27f74eb6"))
                .updatedBy(new ObjectId("66d0a17eb48aebab27f74eb6"))
                .build();

        when(categoryRepository.findById("123")).thenReturn(Optional.of(category));
        ModelCategoryDto modelCategoryDto = categoryDtoMapper.toModelCategoryDto(category.getId());

        assertNotNull(modelCategoryDto);
        assertEquals("123", modelCategoryDto.id());
        assertEquals("electronics", modelCategoryDto.name());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenCategoryInModelCategoryDtoIsNotFound() {
        when(categoryRepository.findById("66d0a8a9b48aebab27f74f5a")).thenReturn(Optional.empty());
        var exception = assertThrows(ResourceNotFoundException.class, () -> categoryDtoMapper.toModelCategoryDto("66d0a8a9b48aebab27f74f5a"));
        assertEquals("Category with id 66d0a8a9b48aebab27f74f5a not found", exception.getMessage());
    }
}
