package com.makbe.ims.dto.supplier;

import com.makbe.ims.dto.user.ModelUserDto;
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
