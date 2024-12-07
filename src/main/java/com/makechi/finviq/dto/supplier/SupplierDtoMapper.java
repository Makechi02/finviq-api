package com.makechi.finviq.dto.supplier;

import com.makechi.finviq.collections.Supplier;
import com.makechi.finviq.dto.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SupplierDtoMapper implements Function<Supplier, SupplierDto> {

    private final UserMapper userMapper;

    @Override
    public SupplierDto apply(Supplier supplier) {
        if (supplier == null)
            throw new NullPointerException("Supplier should not be null");

        return SupplierDto.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .address(supplier.getAddress())
                .phone(supplier.getPhone())
                .addedAt(supplier.getAddedAt())
                .addedBy(userMapper.toModelUserDto(supplier.getAddedBy().toHexString()))
                .addedAt(supplier.getAddedAt())
                .updatedBy(userMapper.toModelUserDto(supplier.getUpdatedBy().toHexString()))
                .updatedAt(supplier.getUpdatedAt())
                .build();
    }

    public ModelSupplierDto toModelSupplierDto(Supplier supplier) {
        if (supplier == null)
            throw new NullPointerException("Supplier should not be null");

        return new ModelSupplierDto(supplier.getId(), supplier.getName());
    }
}
