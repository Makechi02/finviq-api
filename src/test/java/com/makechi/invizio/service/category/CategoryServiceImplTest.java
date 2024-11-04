package com.makechi.invizio.service.category;

import com.makechi.invizio.collections.Category;
import com.makechi.invizio.controller.category.AddUpdateCategoryRequest;
import com.makechi.invizio.dto.category.CategoryDto;
import com.makechi.invizio.dto.category.CategoryDtoMapper;
import com.makechi.invizio.dto.user.ModelUserDto;
import com.makechi.invizio.exception.DuplicateResourceException;
import com.makechi.invizio.exception.RequestValidationException;
import com.makechi.invizio.exception.ResourceNotFoundException;
import com.makechi.invizio.repository.CategoryRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    private final LocalDateTime createdAt = LocalDateTime.now();
    private final LocalDateTime updatedAt = LocalDateTime.now();
    private final ModelUserDto createdBy = new ModelUserDto("670258595df0332b7901a83a", "Makechi Eric");
    private final ModelUserDto updatedBy = new ModelUserDto("670258595df0332b7901a83a", "Makechi Eric");
    private final Category electronicsCategory = Category.builder()
            .id("67025ac415fe1c74664a37c6")
            .name("electronics")
            .createdAt(createdAt)
            .createdBy(new ObjectId("670258595df0332b7901a83a"))
            .updatedAt(updatedAt)
            .updatedBy(new ObjectId("670258595df0332b7901a83a"))
            .build();
    private final Category toysCategory = Category.builder()
            .id("67025ac415fe1c74664a37d7")
            .name("toys")
            .createdAt(createdAt)
            .createdBy(new ObjectId("670258595df0332b7901a83d"))
            .updatedAt(updatedAt)
            .updatedBy(new ObjectId("670258595df0332b7901a83a"))
            .build();
    private final CategoryDto electronicsCategoryDto = CategoryDto.builder()
            .id("67025ac415fe1c74664a37c6")
            .name("electronics")
            .createdAt(createdAt)
            .createdBy(createdBy)
            .updatedAt(updatedAt)
            .updatedBy(updatedBy)
            .build();
    private final CategoryDto toysCategoryDto = CategoryDto.builder()
            .id("67025ac415fe1c74664a37d7")
            .name("toys")
            .createdAt(createdAt)
            .createdBy(createdBy)
            .updatedAt(updatedAt)
            .updatedBy(updatedBy)
            .build();
    private CategoryRepository categoryRepository;
    private CategoryDtoMapper categoryDtoMapper;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryDtoMapper = mock(CategoryDtoMapper.class);
        categoryService = new CategoryServiceImpl(categoryRepository, categoryDtoMapper);
    }

    @Test
    void shouldGetAllCategories() {
        List<Category> categories = List.of(electronicsCategory, toysCategory);

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryDtoMapper.apply(electronicsCategory)).thenReturn(electronicsCategoryDto);
        when(categoryDtoMapper.apply(toysCategory)).thenReturn(toysCategoryDto);

        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();

        assertNotNull(categoryDtoList);
        assertEquals(2, categoryDtoList.size());
        assertEquals(electronicsCategory.getId(), categoryDtoList.getFirst().getId());
    }

    @Test
    void shouldGetCategoryById() {
        when(categoryRepository.findById("67025ac415fe1c74664a37c6")).thenReturn(Optional.of(electronicsCategory));
        when(categoryDtoMapper.apply(electronicsCategory)).thenReturn(electronicsCategoryDto);

        CategoryDto categoryDto = categoryService.getCategoryById("67025ac415fe1c74664a37c6");

        assertNotNull(categoryDto);
        assertEquals("67025ac415fe1c74664a37c6", categoryDto.getId());
        assertEquals("electronics", categoryDto.getName());
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExist() {
        var exception = assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById("67025ac415fe1c74664a37c7"));
        assertEquals("Category with id 67025ac415fe1c74664a37c7 not found", exception.getMessage());
    }

    @Test
    void shouldGetCategoryByName() {
        when(categoryRepository.findByName("electronics")).thenReturn(Optional.of(electronicsCategory));
        when(categoryDtoMapper.apply(electronicsCategory)).thenReturn(electronicsCategoryDto);

        CategoryDto categoryDto = categoryService.getCategoryByName("electronics");

        assertNotNull(categoryDto);
        assertEquals("67025ac415fe1c74664a37c6", categoryDto.getId());
        assertEquals("electronics", categoryDto.getName());
    }

    @Test
    void shouldThrowExceptionWhenNameDoesNotExist() {
        var exception = assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryByName("test_category"));
        assertEquals("Category with name test_category not found", exception.getMessage());
    }

    @Test
    void shouldAddCategory() {
        AddUpdateCategoryRequest request = new AddUpdateCategoryRequest("electronics");
        when(categoryRepository.existsByName(request.name())).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(electronicsCategory);
        when(categoryDtoMapper.apply(electronicsCategory)).thenReturn(electronicsCategoryDto);

        CategoryDto categoryDto = categoryService.addCategory(request);

        assertNotNull(categoryDto);
        assertEquals("electronics", categoryDto.getName());
    }

    @Test
    void shouldThrowErrorWhenNameAlreadyExists() {
        AddUpdateCategoryRequest request = new AddUpdateCategoryRequest("electronics");
        when(categoryRepository.existsByName(request.name())).thenReturn(true);

        var exception = assertThrows(DuplicateResourceException.class, () -> categoryService.addCategory(request));
        assertEquals("Category with name electronics already exists", exception.getMessage());
    }

    @Test
    void shouldUpdateCategory() {
        AddUpdateCategoryRequest request = new AddUpdateCategoryRequest("toys");
        when(categoryRepository.findById("67025ac415fe1c74664a37c6")).thenReturn(Optional.of(electronicsCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(toysCategory);
        when(categoryDtoMapper.apply(toysCategory)).thenReturn(toysCategoryDto);

        CategoryDto categoryDto = categoryService.updateCategory("67025ac415fe1c74664a37c6", request);
        assertNotNull(categoryDto);
        assertEquals(request.name(), categoryDto.getName());
    }

    @Test
    void shouldThrowErrorWhenNoDataChangesInUpdateCategory() {
        AddUpdateCategoryRequest request = new AddUpdateCategoryRequest("electronics");
        when(categoryRepository.findById("67025ac415fe1c74664a37c6")).thenReturn(Optional.of(electronicsCategory));

        var exception = assertThrows(RequestValidationException.class, () -> categoryService.updateCategory("67025ac415fe1c74664a37c6", request));
        assertEquals("No Data changes", exception.getMessage());
    }


    @Test
    void shouldDeleteCategoryById() {
        when(categoryRepository.existsById("67025ac415fe1c74664a37c6")).thenReturn(true);
        categoryService.deleteCategoryById("67025ac415fe1c74664a37c6");

        verify(categoryRepository, times(1)).deleteById("67025ac415fe1c74664a37c6");
    }

    @Test
    void shouldThrowErrorWhenCategoryDoesNotExistWhenDeleting() {
        when(categoryRepository.existsById("67025ac415fe1c74664a37c6")).thenReturn(false);

        var exception = assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategoryById("67025ac415fe1c74664a37c6"));
        assertEquals("Category with id 67025ac415fe1c74664a37c6 not found", exception.getMessage());
        verify(categoryRepository, never()).deleteById(anyString());
    }
}