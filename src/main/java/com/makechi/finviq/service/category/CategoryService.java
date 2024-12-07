package com.makechi.finviq.service.category;

import com.makechi.finviq.controller.category.AddUpdateCategoryRequest;
import com.makechi.finviq.dto.category.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    List<CategoryDto> getAllCategories(String query);

    CategoryDto getCategoryById(String id);

    CategoryDto getCategoryByName(String name);

    CategoryDto addCategory(AddUpdateCategoryRequest request);

    CategoryDto updateCategory(String id, AddUpdateCategoryRequest request);

    void deleteCategoryById(String id);
}
