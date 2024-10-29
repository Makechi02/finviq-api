package com.makechi.invizio.dto.customer;

import com.makechi.invizio.collections.Customer;
import com.makechi.invizio.collections.User;
import com.makechi.invizio.dto.user.ModelUserDto;
import com.makechi.invizio.dto.user.UserMapper;
import com.makechi.invizio.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerDtoMapperTest {

    private CustomerDtoMapper customerDtoMapper;
    private UserRepository userRepository;
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = mock(UserMapper.class);
        userRepository = mock(UserRepository.class);

        customerDtoMapper = new CustomerDtoMapper(userRepository, userMapper);
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

        User user = mock(User.class);
        when(userRepository.findById("670258595df0332b7901a83a")).thenReturn(Optional.of(user));
        when(userMapper.toModelUserDto(user)).thenReturn(mock(ModelUserDto.class));

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