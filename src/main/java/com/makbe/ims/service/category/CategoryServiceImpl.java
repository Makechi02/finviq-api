package com.makbe.ims.service.category;

import com.makbe.ims.collections.Category;
import com.makbe.ims.controller.category.AddUpdateCategoryRequest;
import com.makbe.ims.dto.category.CategoryDto;
import com.makbe.ims.dto.category.CategoryDtoMapper;
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
    private final CategoryDtoMapper categoryDtoMapper;

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        log.info("All categories: {}", categories.size());
        return categories.stream().map(categoryDtoMapper).toList();
    }

    @Override
    public List<CategoryDto> getAllCategories(String query) {
        List<Category> categories = categoryRepository.searchByKeyword(query);
        log.info("Search query: {}", query);
        log.info("Total categories found: {}", categories.size());
        return categories.stream().map(categoryDtoMapper).toList();
    }

    @Override
    public CategoryDto getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
        return categoryDtoMapper.apply(category);
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category with name " + name + " not found"));
        return categoryDtoMapper.apply(category);
    }

    @Override
    public CategoryDto addCategory(AddUpdateCategoryRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("Category with name " + request.name() + " already exists");
        }

        Category category = Category.builder()
                .name(request.name())
                .build();
        category = categoryRepository.save(category);
        log.info("category saved: {}", category);
        return categoryDtoMapper.apply(category);
    }

    @Override
    public CategoryDto updateCategory(String id, AddUpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));

        boolean changes = false;

        if (request.name() != null && !request.name().isBlank() && !category.getName().equals(request.name())) {
            if (categoryRepository.existsByName(request.name())) {
                throw new DuplicateResourceException("Category with name " + request.name() + " already exists");
            }

            category.setName(request.name());

            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No Data changes");
        }

        category = categoryRepository.save(category);
        return categoryDtoMapper.apply(category);
    }

    @Override
    public void deleteCategoryById(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category with id " + id + " not found");
        }
        categoryRepository.deleteById(id);
    }
}
