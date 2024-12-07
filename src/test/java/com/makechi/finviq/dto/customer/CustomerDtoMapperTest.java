package com.makechi.finviq.dto.customer;

import com.makechi.finviq.collections.Customer;
import com.makechi.finviq.dto.user.ModelUserDto;
import com.makechi.finviq.dto.user.UserMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerDtoMapperTest {

    private CustomerDtoMapper customerDtoMapper;
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = mock(UserMapper.class);
        customerDtoMapper = new CustomerDtoMapper(userMapper);
    }

    @Test
    void shouldMapCustomerToCustomerDto() {
        Customer customer = Customer.builder()
                .id("6702fab431a8c90a64446a37")
                .name("Kenya Supermarket Ltd.")
                .contactPerson("John Doe")
                .email("john@kenyasupermarket.com")
                .phone("+254712345678")
                .address("Moi Avenue, Nairobi")
                .addedBy(new ObjectId("670258595df0332b7901a83a"))
                .addedAt(LocalDateTime.now())
                .updatedBy(new ObjectId("670258595df0332b7901a83a"))
                .updatedAt(LocalDateTime.now())
                .build();

        var modelUserDto = new ModelUserDto("670258595df0332b7901a83a", "Makechi Eric");
        when(userMapper.toModelUserDto("670258595df0332b7901a83a")).thenReturn(modelUserDto);

        CustomerDto customerDto = customerDtoMapper.apply(customer);
        assertNotNull(customerDto);
        assertEquals("6702fab431a8c90a64446a37", customerDto.getId());
        assertEquals("Kenya Supermarket Ltd.", customerDto.getName());
        assertEquals("John Doe", customerDto.getContactPerson());
        assertEquals("john@kenyasupermarket.com", customerDto.getEmail());
        assertEquals("+254712345678", customerDto.getPhone());
        assertEquals("Moi Avenue, Nairobi", customerDto.getAddress());
        assertEquals(customer.getAddedAt(), customerDto.getAddedAt());
        assertEquals(customer.getUpdatedAt(), customerDto.getUpdatedAt());
        assertEquals("670258595df0332b7901a83a", customerDto.getAddedBy().id());
        assertEquals("670258595df0332b7901a83a", customerDto.getUpdatedBy().id());
    }

    @Test
    void shouldMapCustomerToModelCustomerDto() {
        Customer customer = Customer.builder()
                .id("6702fab431a8c90a64446a37")
                .name("Kenya Supermarkets Ltd")
                .contactPerson("John Doe")
                .email("john@kenyasupermarket.com")
                .phone("+254712345678")
                .address("Moi Avenue, Nairobi")
                .build();

        ModelCustomerDto modelSupplierDto = customerDtoMapper.toModelCustomerDto(customer);

        assertNotNull(modelSupplierDto);
        assertEquals("6702fab431a8c90a64446a37", modelSupplierDto.id());
        assertEquals("Kenya Supermarkets Ltd", modelSupplierDto.name());
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenCustomerIsNullInModelMapping() {
        var exception = assertThrows(NullPointerException.class, () -> customerDtoMapper.toModelCustomerDto(null));
        assertEquals("Customer should not be null", exception.getMessage());
    }
}