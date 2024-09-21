package com.makbe.ims.dto.supplier;

import com.makbe.ims.collections.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDtoMapperTest {

    private SupplierDtoMapper supplierDtoMapper;

    @BeforeEach
    void setUp() {
        supplierDtoMapper = new SupplierDtoMapper();
    }

    @Test
    void shouldMapSupplierToSupplierDto() {
        Supplier supplier = Supplier.builder()
                .id("123")
                .name("Khwiz Suppliers")
                .build();

        SupplierDto supplierDto = supplierDtoMapper.apply(supplier);

        assertNotNull(supplierDto);
        assertEquals("123", supplierDto.getId());
        assertEquals("Khwiz Suppliers", supplierDto.getName());
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenSupplierIsNull() {
        var exception = assertThrows(NullPointerException.class, () -> supplierDtoMapper.apply(null));
        assertEquals("Supplier should not be null", exception.getMessage());
    }

    @Test
    void shouldHandleEmptyFields() {
        Supplier supplier = Supplier.builder()
                .id(null)
                .name(null)
                .build();

        SupplierDto supplierDto = supplierDtoMapper.apply(supplier);

        assertNotNull(supplierDto);
        assertNull(supplierDto.getId());
        assertNull(supplierDto.getName());
    }

}