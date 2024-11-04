package com.makechi.invizio.dto.supplier;

import com.makechi.invizio.collections.Supplier;
import com.makechi.invizio.dto.user.ModelUserDto;
import com.makechi.invizio.dto.user.UserMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SupplierDtoMapperTest {

    private final Supplier supplier = Supplier.builder()
            .id("supplier123")
            .name("ABC Suppliers")
            .address("123 Market Street")
            .phone("0712345678")
            .addedBy(new ObjectId("66d0a17eb48aebab27f74eb6"))
            .updatedBy(new ObjectId("66d0a17eb48aebab27f74eb6"))
            .build();
    private UserMapper userMapper;
    private SupplierDtoMapper supplierDtoMapper;

    @BeforeEach
    void setUp() {
        userMapper = mock(UserMapper.class);
        supplierDtoMapper = new SupplierDtoMapper(userMapper);
    }

    @Test
    void shouldMapSupplierToSupplierDto() {
        var userDto = new ModelUserDto("66d0a17eb48aebab27f74eb6", "Makechi Eric");
        when(userMapper.toModelUserDto("66d0a17eb48aebab27f74eb6")).thenReturn(userDto);

        SupplierDto supplierDto = supplierDtoMapper.apply(supplier);

        assertNotNull(supplierDto);
        assertEquals("supplier123", supplierDto.getId());
        assertEquals("ABC Suppliers", supplierDto.getName());
        assertEquals("123 Market Street", supplierDto.getAddress());
        assertEquals("0712345678", supplierDto.getPhone());
        assertEquals("66d0a17eb48aebab27f74eb6", supplierDto.getAddedBy().id());
        assertEquals("66d0a17eb48aebab27f74eb6", supplierDto.getUpdatedBy().id());
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenSupplierIsNull() {
        var exception = assertThrows(NullPointerException.class, () -> supplierDtoMapper.apply(null));
        assertEquals("Supplier should not be null", exception.getMessage());
    }

    @Test
    void shouldMapSupplierToModelSupplierDto() {
        ModelSupplierDto modelSupplierDto = supplierDtoMapper.toModelSupplierDto(supplier);

        assertNotNull(modelSupplierDto);
        assertEquals("supplier123", modelSupplierDto.id());
        assertEquals("ABC Suppliers", modelSupplierDto.name());
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenSupplierIsNullInModelMapping() {
        var exception = assertThrows(NullPointerException.class, () -> supplierDtoMapper.toModelSupplierDto(null));
        assertEquals("Supplier should not be null", exception.getMessage());
    }
}
