package com.makbe.ims.dto.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
    private String id;
    private String name;
}
