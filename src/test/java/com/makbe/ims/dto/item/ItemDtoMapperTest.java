package com.makbe.ims.dto.item;

import com.makbe.ims.collections.Category;
import com.makbe.ims.collections.Item;
import com.makbe.ims.collections.Supplier;
import com.makbe.ims.collections.User;
import com.makbe.ims.dto.category.CategoryDtoMapper;
import com.makbe.ims.dto.category.ModelCategoryDto;
import com.makbe.ims.dto.supplier.ModelSupplierDto;
import com.makbe.ims.dto.supplier.SupplierDtoMapper;
import com.makbe.ims.dto.user.ModelUserDto;
import com.makbe.ims.dto.user.UserMapper;
import com.makbe.ims.repository.CategoryRepository;
import com.makbe.ims.repository.SupplierRepository;
import com.makbe.ims.repository.UserRepository;
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
    private SupplierRepository supplierRepository;
    private CategoryDtoMapper categoryDtoMapper;
    private SupplierDtoMapper supplierDtoMapper;
    private UserMapper userMapper;
    private ItemDtoMapper itemDtoMapper;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        supplierRepository = mock(SupplierRepository.class);
        categoryDtoMapper = mock(CategoryDtoMapper.class);
        supplierDtoMapper = mock(SupplierDtoMapper.class);
        userMapper = mock(UserMapper.class);

        itemDtoMapper = new ItemDtoMapper(userRepository, categoryRepository, supplierRepository,
                categoryDtoMapper, supplierDtoMapper, userMapper);
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
                .createdBy("user123")
                .updatedBy("user124")
                .category("category123")
                .supplier("supplier123")
                .build();

        User mockUser = mock(User.class);
        Category mockCategory = mock(Category.class);
        Supplier mockSupplier = mock(Supplier.class);
        var mockCreatedByDto = Mockito.mock(ModelUserDto.class);
        var mockUpdatedByDto = Mockito.mock(ModelUserDto.class);
        var mockCategoryDto = Mockito.mock(ModelCategoryDto.class);
        var mockSupplierDto = Mockito.mock(ModelSupplierDto.class);

        when(userRepository.findById("user123")).thenReturn(Optional.of(mockUser));
        when(userRepository.findById("user124")).thenReturn(Optional.of(mockUser));
        when(categoryRepository.findById("category123")).thenReturn(Optional.of(mockCategory));
        when(supplierRepository.findById("supplier123")).thenReturn(Optional.of(mockSupplier));

        when(userMapper.toModelUserDto(mockUser)).thenReturn(mockCreatedByDto).thenReturn(mockUpdatedByDto);
        when(categoryDtoMapper.toModelCategoryDto(mockCategory)).thenReturn(mockCategoryDto);
        when(supplierDtoMapper.toModelSupplierDto(mockSupplier)).thenReturn(mockSupplierDto);

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
        assertEquals(mockSupplierDto, itemDto.getSupplier());
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenItemIsNull() {
        assertThrows(NullPointerException.class, () -> itemDtoMapper.apply(null));
    }
}
