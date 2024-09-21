package com.makbe.ims.dto.supplier;

import com.makbe.ims.collections.Supplier;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SupplierDtoMapper implements Function<Supplier, SupplierDto> {
    @Override
    public SupplierDto apply(Supplier supplier) {
        if (supplier == null)
            throw new NullPointerException("Supplier should not be null");

        return SupplierDto.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .build();
    }
}
