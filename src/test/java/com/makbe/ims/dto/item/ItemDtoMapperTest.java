package com.makbe.ims.dto.item;

import com.makbe.ims.collections.Category;
import com.makbe.ims.collections.Item;
import com.makbe.ims.collections.Supplier;
import com.makbe.ims.collections.User;
import com.makbe.ims.dto.category.CategoryDto;
import com.makbe.ims.dto.category.CategoryDtoMapper;
import com.makbe.ims.dto.supplier.SupplierDto;
import com.makbe.ims.dto.supplier.SupplierDtoMapper;
import com.makbe.ims.dto.user.ItemUserDto;
import com.makbe.ims.dto.user.UserMapper;
import com.makbe.ims.repository.UserRepository;
import com.makbe.ims.service.category.CategoryService;
import com.makbe.ims.service.supplier.SupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemDtoMapperTest {

    private UserRepository userRepository;
    private CategoryService categoryService;
    private SupplierService supplierService;
    private CategoryDtoMapper categoryDtoMapper;
    private SupplierDtoMapper supplierDtoMapper;
    private UserMapper userMapper;
    private ItemDtoMapper itemDtoMapper;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        categoryService = mock(CategoryService.class);
        supplierService = mock(SupplierService.class);
        categoryDtoMapper = mock(CategoryDtoMapper.class);
        supplierDtoMapper = mock(SupplierDtoMapper.class);
        userMapper = mock(UserMapper.class);

        itemDtoMapper = new ItemDtoMapper(
                userRepository, categoryService, supplierService,
                categoryDtoMapper, supplierDtoMapper, userMapper
        );
    }

    @Test
    void shouldMapItemToItemDto() {
        Item item = Item.builder()
                .id("itemId")
                .brand("BrandX")
                .category("catId")
                .createdAt(LocalDateTime.now())
                .createdBy("userId1")
                .model("ModelX")
                .name("ItemX")
                .price(100.0)
                .quantity(5)
                .sku("SKU1234")
                .supplier("supplierId")
                .stockAlert(10)
                .updatedAt(LocalDateTime.now())
                .updatedBy("userId2")
                .build();

        ItemUserDto createdByDto = ItemUserDto.builder().id("userId1").name("Created User").build();
        ItemUserDto updatedByDto = ItemUserDto.builder().id("userId2").name("Updated User").build();
        SupplierDto supplierDto = SupplierDto.builder().id("supplierId").name("SupplierName").build();
        CategoryDto categoryDto = CategoryDto.builder().id("catId").name("CategoryName").build();

        User user1 = User.builder()
                .id("userId1")
                .name("User 1")
                .build();

        User user2 = User.builder()
                .id("userId2")
                .name("User 2")
                .build();

        Supplier supplier = Supplier.builder().id("supplierID").name("Khwiz Suppliers").build();
        Category electronicsCategory = Category.builder()
                .id("1234")
                .name("electronics")
                .build();

        when(userRepository.findById("userId1")).thenReturn(Optional.of(user1));
        when(userRepository.findById("userId2")).thenReturn(Optional.of(user2));
        when(userMapper.toItemUserDto(any())).thenReturn(createdByDto).thenReturn(updatedByDto);
        when(supplierService.getSupplierById("supplierId")).thenReturn(supplier);
        when(supplierDtoMapper.apply(any())).thenReturn(supplierDto);
        when(categoryService.getCategoryById("catId")).thenReturn(electronicsCategory);
        when(categoryDtoMapper.apply(any())).thenReturn(categoryDto);

        ItemDto itemDto = itemDtoMapper.apply(item);

        assertNotNull(itemDto);
        assertEquals("itemId", itemDto.getId());
        assertEquals("BrandX", itemDto.getBrand());
        assertEquals("ItemX", itemDto.getName());
        assertEquals("ModelX", itemDto.getModel());
        assertEquals(100.0, itemDto.getPrice());
        assertEquals(5, itemDto.getQuantity());
        assertEquals("SKU1234", itemDto.getSku());
        assertEquals(10, itemDto.getStockAlert());

        assertEquals(createdByDto, itemDto.getCreatedBy());
        assertEquals(updatedByDto, itemDto.getUpdatedBy());
        assertEquals(supplierDto, itemDto.getSupplier());
        assertEquals(categoryDto, itemDto.getCategory());

        verify(userRepository).findById("userId1");
        verify(userRepository).findById("userId2");
        verify(userMapper, times(2)).toItemUserDto(any());
        verify(supplierService).getSupplierById("supplierId");
        verify(categoryService).getCategoryById("catId");
    }

    @Test
    void shouldThrowExceptionIfUserNotFound() {
        Item item = Item.builder()
                .id("itemId")
                .createdBy("nonExistingUser")
                .build();

        when(userRepository.findById("nonExistingUser")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> itemDtoMapper.apply(item));
        verify(userRepository).findById("nonExistingUser");
    }
}
