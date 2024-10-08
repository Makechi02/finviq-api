package com.makbe.ims.dto.category;

import com.makbe.ims.collections.Category;
import com.makbe.ims.dto.user.UserMapper;
import com.makbe.ims.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CategoryDtoMapper implements Function<Category, CategoryDto> {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public CategoryDto apply(Category category) {
        if (category == null)
            throw new NullPointerException("Category should not be null");

        var createdBy = userMapper.toModelUserDto(userRepository.findById(category.getCreatedBy().toString()).orElseThrow());
        var updatedBy = userMapper.toModelUserDto(userRepository.findById(category.getUpdatedBy().toString()).orElseThrow());

        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .createdBy(createdBy)
                .createdAt(category.getCreatedAt())
                .updatedBy(updatedBy)
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    public ModelCategoryDto toModelCategoryDto(Category category) {
        if (category == null)
            throw new NullPointerException("Category should not be null");

        return new ModelCategoryDto(category.getId(), category.getName());
    }
}
