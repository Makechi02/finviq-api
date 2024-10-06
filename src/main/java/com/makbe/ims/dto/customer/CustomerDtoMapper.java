package com.makbe.ims.dto.customer;

import com.makbe.ims.collections.Customer;
import com.makbe.ims.dto.user.UserMapper;
import com.makbe.ims.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CustomerDtoMapper implements Function<Customer, CustomerDto> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public CustomerDto apply(Customer customer) {
        var addedBy = userMapper.toModelUserDto(userRepository.findById(customer.getAddedBy().toHexString()).orElseThrow());
        var updatedBy = userMapper.toModelUserDto(userRepository.findById(customer.getUpdatedBy().toHexString()).orElseThrow());

        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .contactPerson(customer.getContactPerson())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .addedBy(addedBy)
                .addedAt(customer.getAddedAt())
                .updatedBy(updatedBy)
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}
