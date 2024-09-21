package com.makbe.ims.dto.supplier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierDto {
    private String id;
    private String name;
}
