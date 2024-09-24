package com.makbe.ims.controller.category;

import com.makbe.ims.collections.Category;
import com.makbe.ims.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories(@RequestParam(value = "query", required = false) String query) {
        if (query != null && !query.isBlank()) {
            return categoryService.getAllCategories(query);
        }
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable String id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/name/{name}")
    public Category getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }

    @PostMapping
    public Category addCategory(@RequestBody AddUpdateCategoryRequest request) {
        return categoryService.addCategory(request);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable String id, @RequestBody AddUpdateCategoryRequest request) {
        return categoryService.updateCategory(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable String id) {
        categoryService.deleteCategoryById(id);
        return "Category deleted successfully";
    }
}
