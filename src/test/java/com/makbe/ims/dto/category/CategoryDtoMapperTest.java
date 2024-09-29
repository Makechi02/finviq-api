package com.makbe.ims.dto.category;

import com.makbe.ims.collections.Category;
import com.makbe.ims.collections.User;
import com.makbe.ims.dto.user.ModelUserDto;
import com.makbe.ims.dto.user.UserMapper;
import com.makbe.ims.repository.UserRepository;
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
    private UserMapper userMapper;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userMapper = mock(UserMapper.class);
        userRepository = mock(UserRepository.class);

        categoryDtoMapper = new CategoryDtoMapper(userMapper, userRepository);
    }

    @Test
    void shouldMapCategoryToCategoryDto() {
        Category category = Category.builder()
                .id("123")
                .name("electronics")
                .createdBy(new ObjectId("66d0a17eb48aebab27f74eb6"))
                .updatedBy(new ObjectId("66d0a17eb48aebab27f74eb6"))
                .build();

        User mockUser = mock(User.class);

        when(userRepository.findById("66d0a17eb48aebab27f74eb6")).thenReturn(Optional.of(mockUser));

        when(userMapper.toModelUserDto(mockUser)).thenReturn(Mockito.mock(ModelUserDto.class));

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
                .createdBy(new ObjectId("66d0a17eb48aebab27f74eb6"))
                .updatedBy(new ObjectId("66d0a17eb48aebab27f74eb6"))
                .build();

        User mockUser = mock(User.class);

        when(userRepository.findById("66d0a17eb48aebab27f74eb6")).thenReturn(Optional.of(mockUser));

        when(userMapper.toModelUserDto(mockUser)).thenReturn(Mockito.mock(ModelUserDto.class));

        CategoryDto categoryDto = categoryDtoMapper.apply(category);

        assertNotNull(categoryDto);
        assertNull(categoryDto.getId());
        assertNull(categoryDto.getName());
    }
}
