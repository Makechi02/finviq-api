package com.makechi.finviq.dto.supplier;

import com.makechi.finviq.dto.user.ModelUserDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SupplierDto {
    private String id;
    private String name;
    private String address;
    private String phone;
    private LocalDateTime addedAt;
    private ModelUserDto addedBy;
    private LocalDateTime updatedAt;
    private ModelUserDto updatedBy;
}
