package com.makechi.invizio.controller.category;

import com.makechi.invizio.dto.category.CategoryDto;
import com.makechi.invizio.service.category.CategoryService;
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
    public List<CategoryDto> getAllCategories(@RequestParam(value = "query", required = false) String query) {
        if (query != null && !query.isBlank()) {
            return categoryService.getAllCategories(query);
        }
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable String id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/name/{name}")
    public CategoryDto getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody AddUpdateCategoryRequest request) {
        return categoryService.addCategory(request);
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable String id, @RequestBody AddUpdateCategoryRequest request) {
        return categoryService.updateCategory(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable String id) {
        categoryService.deleteCategoryById(id);
        return "Category deleted successfully";
    }
}
