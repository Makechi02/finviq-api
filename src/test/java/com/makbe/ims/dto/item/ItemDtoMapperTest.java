package com.makbe.ims.dto.item;

import com.makbe.ims.collections.Category;
import com.makbe.ims.collections.Item;
import com.makbe.ims.collections.User;
import com.makbe.ims.dto.category.CategoryDtoMapper;
import com.makbe.ims.dto.category.ModelCategoryDto;
import com.makbe.ims.dto.user.ModelUserDto;
import com.makbe.ims.dto.user.UserMapper;
import com.makbe.ims.repository.CategoryRepository;
import com.makbe.ims.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemDtoMapperTest {

    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private CategoryDtoMapper categoryDtoMapper;
    private UserMapper userMapper;
    private ItemDtoMapper itemDtoMapper;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        categoryDtoMapper = mock(CategoryDtoMapper.class);
        userMapper = mock(UserMapper.class);

        itemDtoMapper = new ItemDtoMapper(userRepository, userMapper, categoryRepository, categoryDtoMapper);
    }

    @Test
    void shouldMapItemToItemDto() {
        Item item = Item.builder()
                .id("item123")
                .brand("Samsung")
                .model("Galaxy S21")
                .name("Smartphone")
                .price(799.99)
                .quantity(10)
                .sku("S21-001")
                .stockAlert(5)
                .createdBy(new ObjectId("66d0a17eb48aebab27f74eb6"))
                .updatedBy(new ObjectId("66d0a17eb48aebab27f74eb6"))
                .category(new ObjectId("66d0a8a9b48aebab27f74f5a"))
                .build();

        User mockUser = mock(User.class);
        Category mockCategory = mock(Category.class);
        var mockCreatedByDto = Mockito.mock(ModelUserDto.class);
        var mockUpdatedByDto = Mockito.mock(ModelUserDto.class);
        var mockCategoryDto = Mockito.mock(ModelCategoryDto.class);

        when(userRepository.findById("66d0a17eb48aebab27f74eb6")).thenReturn(Optional.of(mockUser));
        when(categoryRepository.findById("66d0a8a9b48aebab27f74f5a")).thenReturn(Optional.of(mockCategory));

        when(userMapper.toModelUserDto(mockUser)).thenReturn(mockCreatedByDto).thenReturn(mockUpdatedByDto);
        when(categoryDtoMapper.toModelCategoryDto(mockCategory)).thenReturn(mockCategoryDto);

        ItemDto itemDto = itemDtoMapper.apply(item);

        assertNotNull(itemDto);
        assertEquals("item123", itemDto.getId());
        assertEquals("Samsung", itemDto.getBrand());
        assertEquals("Galaxy S21", itemDto.getModel());
        assertEquals("Smartphone", itemDto.getName());
        assertEquals(799.99, itemDto.getPrice());
        assertEquals(10, itemDto.getQuantity());
        assertEquals("S21-001", itemDto.getSku());
        assertEquals(5, itemDto.getStockAlert());
        assertEquals(mockCreatedByDto, itemDto.getCreatedBy());
        assertEquals(mockUpdatedByDto, itemDto.getUpdatedBy());
        assertEquals(mockCategoryDto, itemDto.getCategory());
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenItemIsNull() {
        assertThrows(NullPointerException.class, () -> itemDtoMapper.apply(null));
    }
}
