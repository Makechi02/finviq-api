package com.makbe.ims.service.category;

import com.makbe.ims.collections.Category;
import com.makbe.ims.controller.category.AddUpdateCategoryRequest;
import com.makbe.ims.exception.DuplicateResourceException;
import com.makbe.ims.exception.RequestValidationException;
import com.makbe.ims.exception.ResourceNotFoundException;
import com.makbe.ims.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        log.info("Total categories: {}", categories.size());
        return categories;
    }

    @Override
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category with name " + name + " not found"));
    }

    @Override
    public Category addCategory(AddUpdateCategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Category with name " + request.getName() + " already exists");
        }

        Category category = Category.builder()
                .name(request.getName())
                .build();
        category = categoryRepository.save(category);
        log.info("category saved: {}", category);
        return category;
    }

    @Override
    public Category updateCategory(String id, AddUpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));

        boolean changes = false;

        if (request.getName() != null && !request.getName().isBlank() && !category.getName().equals(request.getName())) {
            if (categoryRepository.existsByName(request.getName())) {
                throw new DuplicateResourceException("Category with name " + request.getName() + " already exists");
            }

            category.setName(request.getName());

            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No Data changes");
        }

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category with id " + id + " not found");
        }
        categoryRepository.deleteById(id);
    }
}
