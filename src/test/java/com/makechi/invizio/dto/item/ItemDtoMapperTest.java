package com.makechi.invizio.dto.item;

import com.makechi.invizio.collections.item.Item;
import com.makechi.invizio.dto.category.CategoryDtoMapper;
import com.makechi.invizio.dto.category.ModelCategoryDto;
import com.makechi.invizio.dto.user.ModelUserDto;
import com.makechi.invizio.dto.user.UserMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemDtoMapperTest {

    private CategoryDtoMapper categoryDtoMapper;
    private UserMapper userMapper;
    private ItemDtoMapper itemDtoMapper;

    @BeforeEach
    void setUp() {
        categoryDtoMapper = mock(CategoryDtoMapper.class);
        userMapper = mock(UserMapper.class);
        itemDtoMapper = new ItemDtoMapper(userMapper, categoryDtoMapper);
    }

    @Test
    void shouldMapItemToItemDto() {
        Item item = Item.builder()
                .id("item123")
                .brand("Samsung")
                .model("Galaxy S21")
                .name("Smartphone")
                .costPrice(BigDecimal.valueOf(799.99))
                .retailPrice(BigDecimal.valueOf(820))
                .vatInclusivePrice(BigDecimal.valueOf(900))
                .quantity(10)
                .sku("S21-001")
                .stockAlert(5)
                .createdBy(new ObjectId("66d0a17eb48aebab27f74eb6"))
                .updatedBy(new ObjectId("66d0a17eb48aebab27f74eb6"))
                .category(new ObjectId("66d0a8a9b48aebab27f74f5a"))
                .build();

        var userDto = new ModelUserDto("66d0a17eb48aebab27f74eb6", "Makechi Eric");
        var category = new ModelCategoryDto("66d0a8a9b48aebab27f74f5a", "electronics");

        when(userMapper.toModelUserDto("66d0a17eb48aebab27f74eb6")).thenReturn(userDto);
        when(categoryDtoMapper.toModelCategoryDto("66d0a8a9b48aebab27f74f5a")).thenReturn(category);

        ItemDto itemDto = itemDtoMapper.apply(item);

        assertNotNull(itemDto);
        assertEquals("item123", itemDto.getId());
        assertEquals("Samsung", itemDto.getBrand());
        assertEquals("Galaxy S21", itemDto.getModel());
        assertEquals("Smartphone", itemDto.getName());
        assertEquals(BigDecimal.valueOf(799.99), itemDto.getCostPrice());
        assertEquals(BigDecimal.valueOf(820), itemDto.getRetailPrice());
        assertEquals(BigDecimal.valueOf(900), itemDto.getVatInclusivePrice());
        assertEquals(10, itemDto.getQuantity());
        assertEquals("S21-001", itemDto.getSku());
        assertEquals(5, itemDto.getStockAlert());
        assertEquals("66d0a17eb48aebab27f74eb6", itemDto.getCreatedBy().id());
        assertEquals("66d0a17eb48aebab27f74eb6", itemDto.getUpdatedBy().id());
        assertEquals("66d0a8a9b48aebab27f74f5a", itemDto.getCategory().id());
        assertEquals("electronics", itemDto.getCategory().name());
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenItemIsNull() {
        assertThrows(NullPointerException.class, () -> itemDtoMapper.apply(null));
    }
}
