package com.makechi.finviq.dto.customer;

import com.makechi.finviq.collections.Customer;
import com.makechi.finviq.dto.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CustomerDtoMapper implements Function<Customer, CustomerDto> {

    private final UserMapper userMapper;

    @Override
    public CustomerDto apply(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .contactPerson(customer.getContactPerson())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .addedBy(userMapper.toModelUserDto(customer.getAddedBy().toHexString()))
                .addedAt(customer.getAddedAt())
                .updatedBy(userMapper.toModelUserDto(customer.getUpdatedBy().toHexString()))
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    public ModelCustomerDto toModelCustomerDto(Customer customer) {
        if (customer == null)
            throw new NullPointerException("Customer should not be null");

        return new ModelCustomerDto(customer.getId(), customer.getName());
    }
}
