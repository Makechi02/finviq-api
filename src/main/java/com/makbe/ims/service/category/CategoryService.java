package com.makbe.ims.service.category;

import com.makbe.ims.collections.Category;
import com.makbe.ims.controller.category.AddUpdateCategoryRequest;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    List<Category> getAllCategories(String query);

    Category getCategoryById(String id);

    Category getCategoryByName(String name);

    Category addCategory(AddUpdateCategoryRequest request);

    Category updateCategory(String id, AddUpdateCategoryRequest request);

    void deleteCategoryById(String id);
}
