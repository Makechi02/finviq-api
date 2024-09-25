package com.makbe.ims.dto.supplier;

import com.makbe.ims.collections.Supplier;
import com.makbe.ims.dto.user.UserMapper;
import com.makbe.ims.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SupplierDtoMapper implements Function<Supplier, SupplierDto> {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public SupplierDto apply(Supplier supplier) {
        if (supplier == null)
            throw new NullPointerException("Supplier should not be null");

        var addedBy = userMapper.toModelUserDto(userRepository.findById(supplier.getAddedBy()).orElseThrow());
        var updatedBy = userMapper.toModelUserDto(userRepository.findById(supplier.getUpdatedBy()).orElseThrow());

        return SupplierDto.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .address(supplier.getAddress())
                .phone(supplier.getPhone())
                .addedAt(supplier.getAddedAt())
                .addedBy(addedBy)
                .addedAt(supplier.getAddedAt())
                .updatedBy(updatedBy)
                .updatedAt(supplier.getUpdatedAt())
                .build();
    }

    public ModelSupplierDto toModelSupplierDto(Supplier supplier) {
        if (supplier == null)
            throw new NullPointerException("Supplier should not be null");

        return ModelSupplierDto.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .build();
    }
}
