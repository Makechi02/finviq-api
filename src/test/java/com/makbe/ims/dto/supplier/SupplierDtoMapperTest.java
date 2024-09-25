package com.makbe.ims.dto.supplier;

import com.makbe.ims.collections.Supplier;
import com.makbe.ims.collections.User;
import com.makbe.ims.dto.user.ModelUserDto;
import com.makbe.ims.dto.user.UserMapper;
import com.makbe.ims.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SupplierDtoMapperTest {

    private UserMapper userMapper;
    private UserRepository userRepository;
    private SupplierDtoMapper supplierDtoMapper;

    @BeforeEach
    void setUp() {
        userMapper = mock(UserMapper.class);
        userRepository = mock(UserRepository.class);
        supplierDtoMapper = new SupplierDtoMapper(userMapper, userRepository);
    }

    @Test
    void shouldMapSupplierToSupplierDto() {
        Supplier supplier = Supplier.builder()
                .id("supplier123")
                .name("ABC Suppliers")
                .address("123 Market Street")
                .phone("0712345678")
                .addedBy("user123")
                .updatedBy("user124")
                .build();

        User mockUser = mock(User.class);
        var mockAddedByDto = Mockito.mock(ModelUserDto.class);
        var mockUpdatedByDto = Mockito.mock(ModelUserDto.class);

        when(userRepository.findById("user123")).thenReturn(Optional.of(mockUser));
        when(userRepository.findById("user124")).thenReturn(Optional.of(mockUser));
        when(userMapper.toModelUserDto(mockUser)).thenReturn(mockAddedByDto).thenReturn(mockUpdatedByDto);

        SupplierDto supplierDto = supplierDtoMapper.apply(supplier);

        assertNotNull(supplierDto);
        assertEquals("supplier123", supplierDto.getId());
        assertEquals("ABC Suppliers", supplierDto.getName());
        assertEquals("123 Market Street", supplierDto.getAddress());
        assertEquals("0712345678", supplierDto.getPhone());
        assertEquals(mockAddedByDto, supplierDto.getAddedBy());
        assertEquals(mockUpdatedByDto, supplierDto.getUpdatedBy());
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenSupplierIsNull() {
        var exception = assertThrows(NullPointerException.class, () -> supplierDtoMapper.apply(null));
        assertEquals("Supplier should not be null", exception.getMessage());
    }

    @Test
    void shouldMapSupplierToModelSupplierDto() {
        Supplier supplier = Supplier.builder()
                .id("supplier123")
                .name("ABC Suppliers")
                .build();

        ModelSupplierDto modelSupplierDto = supplierDtoMapper.toModelSupplierDto(supplier);

        assertNotNull(modelSupplierDto);
        assertEquals("supplier123", modelSupplierDto.getId());
        assertEquals("ABC Suppliers", modelSupplierDto.getName());
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenSupplierIsNullInModelMapping() {
        var exception = assertThrows(NullPointerException.class, () -> supplierDtoMapper.toModelSupplierDto(null));
        assertEquals("Supplier should not be null", exception.getMessage());
    }
}
